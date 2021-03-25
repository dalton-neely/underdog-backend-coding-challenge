package dalton.neely.playerbackend;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import static dalton.neely.playerbackend.Player.fromCbsPlayer;
import static java.math.RoundingMode.HALF_UP;
import static java.util.Objects.requireNonNull;

@Service
public class PersistService {
  private final CbsClient cbsClient;
  private final PlayerRepository playerRepository;
  private final Map<String, Integer> sums;
  private final Map<String, Integer> amounts;
  private final Map<String, Double> averages;
  
  public PersistService(CbsClient cbsClient, PlayerRepository playerRepository){
    this.cbsClient = cbsClient;
    this.playerRepository = playerRepository;
    this.sums = new HashMap<>();
    this.amounts = new HashMap<>();
    this.averages = new HashMap<>();
  }
  
  @Scheduled(fixedDelay = 900_000) // 15 minutes
  public void persist() throws URISyntaxException {
    System.out.println("Refreshing Database With Player Data...");
    for(Sport sport: Sport.values()) {
      List<CbsResponsePlayer> players = requireNonNull(cbsClient.getPlayers(sport).getBody()).getBody().getPlayers();
  
      for (CbsResponsePlayer player : players) {
        String currentPosition = player.getPosition();
        int currentAge = player.getAge();
        
        if (sums.containsKey(currentPosition)) {
            amounts.replace(currentPosition, amounts.get(currentPosition) + 1);
            sums.replace(currentPosition, sums.get(currentPosition) + currentAge);
        } else {
          amounts.put(currentPosition, 1);
          sums.put(currentPosition, currentAge);
        }
      }
      calculateAverages();
      for (CbsResponsePlayer player : players) {
        Player entity = fromCbsPlayer(player,  round(player.getAge() - averages.get(player.getPosition())), sport);
        if (!playerRepository.existsByIdentification(entity.getId())) {
          playerRepository.save(entity);
        } else {
          playerRepository.updateByIdentification(entity.getId(), entity.getNameBrief(), entity.getFirstName(), entity.getLastName(), entity.getPosition(), entity.getAge(), entity.getAveragePositionAgeDiff());
        }
      }
      reset();
    }
  }
  
  private double round(double value) {
    BigDecimal bigDecimal = BigDecimal.valueOf(value);
    bigDecimal = bigDecimal.setScale(2, HALF_UP);
    return bigDecimal.doubleValue();
  }
  
  private void calculateAverages() {
    Set<String> keys = sums.keySet();
    for(String key: keys){
      int sum = sums.get(key);
      int amount = amounts.get(key);
      double average = round((double) sum / amount);
      averages.put(key, average);
    }
  }
  
  private void reset(){
    this.sums.clear();
    this.amounts.clear();
    this.averages.clear();
  }
}
