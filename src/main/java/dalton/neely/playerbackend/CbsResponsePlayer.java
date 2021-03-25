package dalton.neely.playerbackend;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CbsResponsePlayer {
  @SuppressWarnings("SpellCheckingInspection")
  private String fullname;
  private String position;
  @JsonProperty("eligible_for_offense_and_defense")
  private int eligibleForOffenseAndDefense;
  private String id;
  private String bats;
  private String lastname;
  private String photo;
  @JsonProperty("elias_id")
  private String eliasId;
  @SuppressWarnings("SpellCheckingInspection")
  @JsonProperty("throws")
  private String throwz;
  private String firstname;
  @JsonProperty("pro_status")
  private String proStatus;
  @JsonProperty("pro_team")
  private String proTeam;
  private int age;
  private String jersey;
  private CbsResponseIcons icons;
}
