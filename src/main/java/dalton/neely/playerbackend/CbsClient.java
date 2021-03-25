package dalton.neely.playerbackend;

import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import static dalton.neely.playerbackend.Sport.BASEBALL;
import static dalton.neely.playerbackend.Sport.BASKETBALL;
import static dalton.neely.playerbackend.Sport.FOOTBALL;
import static org.springframework.http.HttpMethod.GET;

public class CbsClient {
  private final RestTemplate restTemplate;
  
  public CbsClient(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }
  
  public ResponseEntity<CbsResponse> getPlayers(Sport sport) throws URISyntaxException {
    RequestEntity<CbsResponse> requestEntity = new RequestEntity<>(GET, new URI(constructUri(sport)));
    return restTemplate.exchange(requestEntity, CbsResponse.class);
  }
  
  private String constructUri(Sport sport){
    return "https://api.cbssports.com/fantasy/players/list?version=3.0&SPORT=" + sport.toString().toLowerCase() + "&response_format=JSON";
  }
}
