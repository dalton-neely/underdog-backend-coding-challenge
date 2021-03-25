package dalton.neely.playerbackend;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
public class PlayerController {
  private final String API_BASE = "/api/v1";
  private final PlayerRepository repository;
  
  public PlayerController(PlayerRepository repository){
    this.repository = repository;
  }
  
  @GetMapping(produces = APPLICATION_JSON_VALUE, path = API_BASE + "/players/{id}")
  public ResponseEntity<Player> getById(@PathVariable String id){
    return ok(repository.findByIdentification(id));
  }
  
  @GetMapping(produces = APPLICATION_JSON_VALUE, path = API_BASE + "/search")
  public ResponseEntity<List<Player>> search(
      @Nullable @RequestParam Sport sport,
      @Nullable @RequestParam String lastInitial,
      @Nullable @RequestParam Integer age,
      @Nullable @RequestParam Integer ageStart,
      @Nullable @RequestParam Integer ageEnd,
      @Nullable @RequestParam String position
  ){
    List<Player> potentialMatches = new ArrayList<>();
    if(sport != null){
      potentialMatches = repository.findAllBySport(sport.toString());
    }
    if(lastInitial != null){
      if(potentialMatches.isEmpty()){
        potentialMatches = repository.findAllByLastNameLike(lastInitial + "%");
      } else {
        potentialMatches = potentialMatches.stream().filter(player -> player.getLastName().toLowerCase().startsWith(lastInitial.toLowerCase())).collect(Collectors.toList());
      }
    }
    if(age != null){
      if(potentialMatches.isEmpty()){
        potentialMatches = repository.findAllByAge(age);
      } else {
        potentialMatches = potentialMatches.stream().filter(players -> players.getAge() == age).collect(Collectors.toList());
      }
    }
    if(ageStart != null){
      if(potentialMatches.isEmpty()){
        potentialMatches = repository.findAllByAgeGreaterThanEqual(ageStart);
      } else {
        potentialMatches = potentialMatches.stream().filter(players -> players.getAge() >= ageStart).collect(Collectors.toList());
      }
    }
    if(ageEnd != null){
      if(potentialMatches.isEmpty()){
        potentialMatches = repository.findAllByAgeLessThanEqual(ageEnd);
      } else {
        potentialMatches = potentialMatches.stream().filter(players -> players.getAge() <= ageEnd).collect(Collectors.toList());
      }
    }
    if(position != null){
      if(potentialMatches.isEmpty()){
        potentialMatches = repository.findAllByPosition(position);
      } else {
        potentialMatches = potentialMatches.stream().filter(players -> players.getPosition().equals(position)).collect(Collectors.toList());
      }
    }
    
    return ok(potentialMatches);
  }
}
