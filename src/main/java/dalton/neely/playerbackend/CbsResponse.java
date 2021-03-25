package dalton.neely.playerbackend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CbsResponse {
  private String uri;
  private String statusMessage;
  private int statusCode;
  private String uriAlias;
  private CbsResponseBody body;
}
