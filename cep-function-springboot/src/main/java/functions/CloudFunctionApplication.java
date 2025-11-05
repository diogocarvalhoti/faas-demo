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
      String cep = inputMessage.getPayload();

      // Validação básica do CEP (8 dígitos)
      if (cep == null || !cep.matches("\\d{8}")) {
        return "{\"erro\": \"CEP inválido. Use 8 dígitos.\"}";
      }

      // Consulta API ViaCEP
      String apiUrl = "https://viacep.com.br/ws/" + cep + "/json/";
      RestTemplate restTemplate = new RestTemplate();

      try {
        String resultado = restTemplate.getForObject(apiUrl, String.class);

        // Verifica se o CEP foi encontrado
        if (resultado != null && resultado.contains("\"erro\"")) {
          return "{\"erro\": \"CEP não encontrado.\"}";
        }

        return resultado;
      } catch (Exception e) {
        return "{\"erro\": \"Erro ao consultar API: " + e.getMessage() + "\"}";
      }
    };
  }
}
