package dalton.neely.playerbackend;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CbsResponseBody {
  private List<CbsResponsePlayer> players;
}
