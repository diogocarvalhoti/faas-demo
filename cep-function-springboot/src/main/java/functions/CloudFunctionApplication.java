package functions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.web.client.RestTemplate;

import java.util.function.Function;

@SpringBootApplication
public class CloudFunctionApplication {

  public static void main(String[] args) {
    SpringApplication.run(CloudFunctionApplication.class, args);
  }

  @Bean
  public Function<Message<String>, String> cep() {
    return (inputMessage) -> {
      String apiUrl = "https://viacep.com.br/ws/" + inputMessage.getPayload() + "/json/";
      return  new RestTemplate().getForObject(apiUrl, String.class);
    };
  }
}
