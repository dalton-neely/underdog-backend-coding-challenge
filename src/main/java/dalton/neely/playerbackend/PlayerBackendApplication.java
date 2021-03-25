package dalton.neely.playerbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling
public class PlayerBackendApplication {
  public static void main(String[] args) {
    SpringApplication.run(PlayerBackendApplication.class, args);
  }
  
  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
  
  @Bean
  public CbsClient cbsClient(RestTemplate restTemplate) {
    return new CbsClient(restTemplate);
  }
  
}
