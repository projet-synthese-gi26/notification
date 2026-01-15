package notification.service.yowyob.inc.notification;

import notification.service.yowyob.inc.notification.application.domain.enums.NotificationType;
import notification.service.yowyob.inc.notification.application.domain.model.ServiceApp;
import notification.service.yowyob.inc.notification.application.domain.repository.ServiceAppRepository;
import notification.service.yowyob.inc.notification.application.port.input.dto.TemplateCreateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class TemplateControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ServiceAppRepository serviceAppRepository;

    private ServiceApp serviceApp;

    @BeforeEach
    void setUp() {
        ServiceApp app = new ServiceApp();
        app.setName("Test App");
        app.setToken(UUID.randomUUID());
        serviceApp = serviceAppRepository.save(app).block();
    }

    @Test
    void testCreateWhatsappTemplate() {
        TemplateCreateRequest request = new TemplateCreateRequest();
        request.setName("Whatsapp Template");
        request.setType(NotificationType.WHATSAPP);
        request.setBody("Hello {{name}}");
        request.setDescription("A whatsapp template");

        webTestClient.post()
                .uri("/api/v1/templates")
                .header("X-Service-Token", serviceApp.getToken().toString())
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Whatsapp Template")
                .jsonPath("$.body").isEqualTo("Hello {{name}}");
    }

    @Test
    void testCreateEmailTemplate() {
        TemplateCreateRequest request = new TemplateCreateRequest();
        request.setName("Email Template");
        request.setType(NotificationType.EMAIL);
        request.setFromEmail("from@example.com");
        request.setSubject("Test Subject");
        request.setBodyHtml("<h1>Hello {{name}}</h1>");
        request.setDescription("An email template");

        webTestClient.post()
                .uri("/api/v1/templates")
                .header("X-Service-Token", serviceApp.getToken().toString())
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Email Template")
                .jsonPath("$.subject").isEqualTo("Test Subject");
    }

    @Test
    void testCreateSmsTemplate() {
        TemplateCreateRequest request = new TemplateCreateRequest();
        request.setName("SMS Template");
        request.setType(NotificationType.SMS);
        request.setMessage("Hello {{name}}");
        request.setDescription("An sms template");

        webTestClient.post()
                .uri("/api/v1/templates")
                .header("X-Service-Token", serviceApp.getToken().toString())
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.name").isEqualTo("SMS Template")
                .jsonPath("$.message").isEqualTo("Hello {{name}}");
    }

    @Test
    void testCreatePullTemplate() {
        TemplateCreateRequest request = new TemplateCreateRequest();
        request.setName("Pull Template");
        request.setType(NotificationType.PULL);
        request.setMessage("Hello {{name}}");
        request.setDescription("A pull template");

        webTestClient.post()
                .uri("/api/v1/templates")
                .header("X-Service-Token", serviceApp.getToken().toString())
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Pull Template")
                .jsonPath("$.message").isEqualTo("Hello {{name}}");
    }
}
