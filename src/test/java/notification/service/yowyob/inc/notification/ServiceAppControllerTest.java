package notification.service.yowyob.inc.notification;

import notification.service.yowyob.inc.notification.application.port.input.dto.ServiceCreateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class ServiceAppControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testRegisterServiceApp() {
        ServiceCreateRequest request = new ServiceCreateRequest();
        request.setName("My New App");
        request.setEmailServerHost("smtp.example.com");
        request.setEmailServerPort("587");
        request.setEmailUsername("user");
        request.setEmailPassword("pass");
        request.setSmsServerHost("sms.example.com");
        request.setSmsServerPort("8080");
        request.setSmstoken("smstoken");
        request.setWhatsappApiUrl("whatsapp.example.com");
        request.setWhatsappApiTokenInstance("whatsapptoken");
        request.setWhatsappIdInstance("whatsappinstance");

        webTestClient.post()
                .uri("/api/v1/services")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("My New App")
                .jsonPath("$.token").isNotEmpty();
    }
}
