package dalton.neely.playerbackend;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import static javax.persistence.GenerationType.*;

@Entity
@Table(name = "Players")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Players {
  @Id
  @GeneratedValue(strategy = AUTO)
  private long index;
  
  @Column(unique=true)
  private String id;
  @JsonProperty(value = "name_brief")
  private String nameBrief;
  @JsonProperty(value = "first_name")
  private String firstName;
  @JsonProperty(value = "last_name")
  private String lastName;
  private String position;
  private int age;
  @JsonProperty(value = "average_position_age_diff")
  private double averagePositionAgeDiff;
  
  public static Players fromCbsPlayer(CbsResponsePlayer player, Double diff){
    String nameBrief = "";
    String first = player.getFirstname().toUpperCase();
    String last = player.getLastname().toUpperCase();
    if(!first.equals("")){
      nameBrief += first.charAt(0) + ".";
    }
    if(!last.equals("")){
      if(!nameBrief.equals("")){
        nameBrief += " ";
      }
      nameBrief += last.charAt(0) + ".";
    }
    return Players.builder()
        .id(player.getId())
        .nameBrief(nameBrief)
        .firstName(player.getFirstname())
        .lastName(player.getLastname())
        .position(player.getPosition())
        .age(player.getAge())
        .averagePositionAgeDiff(diff)
        .build();
  }
}
