package dalton.neely.playerbackend;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import static dalton.neely.playerbackend.Players.fromCbsPlayer;
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
  
  @Scheduled(fixedDelay = 30_000)
  public void persist() throws URISyntaxException {
    System.out.println("Refreshing Database With Player Data...");
    List<CbsResponsePlayer> players = requireNonNull(cbsClient.getBaseballPlayers().getBody()).getBody().getPlayers();
    
    for(CbsResponsePlayer player : players){
      String currentPosition = player.getPosition();
      int currentAge = player.getAge();
      if(sums.containsKey(currentPosition)){
        if(!playerRepository.existsByIdentification(player.getId())) {
          amounts.replace(currentPosition, amounts.get(currentPosition) + 1);
          sums.replace(currentPosition, sums.get(currentPosition) + currentAge);
        }
      } else {
        amounts.put(currentPosition, 1);
        sums.put(currentPosition, currentAge);
      }
    }
    calculateAverages();
    for(CbsResponsePlayer player : players){
      Players entity = fromCbsPlayer(player, averages.get(player.getPosition()) - player.getAge());
      if(!playerRepository.existsByIdentification(entity.getId())){
        playerRepository.save(entity);
      } else {
        playerRepository.updateByIdentification(entity.getId(), entity.getNameBrief(), entity.getFirstName(), entity.getLastName(), entity.getPosition(), entity.getAge(), entity.getAveragePositionAgeDiff());
      }
    }
  }
  
  private double round(double value, int places) {
    BigDecimal bigDecimal = BigDecimal.valueOf(value);
    bigDecimal = bigDecimal.setScale(places, HALF_UP);
    return bigDecimal.doubleValue();
  }
  
  private void calculateAverages() {
    Set<String> keys = sums.keySet();
    for(String key: keys){
      int sum = sums.get(key);
      int amount = amounts.get(key);
      double average = round((double) sum / amount, 2);
      averages.put(key, average);
      System.out.println("Average age for the position " + key + " is " + average);
    }
  }
}
