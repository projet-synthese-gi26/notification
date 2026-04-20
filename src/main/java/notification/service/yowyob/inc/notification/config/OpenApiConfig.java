package notification.service.yowyob.inc.notification.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

import java.util.List; 

import org.springframework.beans.factory.annotation.Value; 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  // Injection de l'URL avec la variable d'environnement
  @Value("${app.swagger.server-url}")
  private String serverUrl;

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("Notification Service API")
            .version("v1.0")
            .description("API for managing notifications, services, and templates.")
            .license(new License().name("Apache 2.0").url("http://springdoc.org")))
        .servers(List.of(
            new Server().url(serverUrl).description("Serveur de Production / Proxy"),
            new Server().url("http://localhost:8080").description("Serveur Local")
        ));
  }
}