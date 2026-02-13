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

import java.util.Map;
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

        @Test
        void testGetTemplatePreviews() {
                TemplateCreateRequest smsRequest = new TemplateCreateRequest();
                smsRequest.setName("SMS Template 1");
                smsRequest.setType(NotificationType.SMS);
                smsRequest.setMessage("Hello from SMS");
                smsRequest.setDescription("A test SMS template");

                TemplateCreateRequest emailRequest = new TemplateCreateRequest();
                emailRequest.setName("Email Template 1");
                emailRequest.setType(NotificationType.EMAIL);
                emailRequest.setFromEmail("test@example.com");
                emailRequest.setSubject("Test Email");
                emailRequest.setBodyHtml("<h1>Hello from Email</h1>");
                emailRequest.setDescription("A test Email template");

                webTestClient.post()
                                .uri("/api/v1/templates")
                                .header("X-Service-Token", serviceApp.getToken().toString())
                                .bodyValue(smsRequest)
                                .exchange()
                                .expectStatus().isCreated();

                webTestClient.post()
                                .uri("/api/v1/templates")
                                .header("X-Service-Token", serviceApp.getToken().toString())
                                .bodyValue(emailRequest)
                                .exchange()
                                .expectStatus().isCreated();

                webTestClient.get()
                                .uri("/api/v1/templates/previews")
                                .header("X-Service-Token", serviceApp.getToken().toString())
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody()
                                .jsonPath("$").isArray()
                                .jsonPath("$.length()").isEqualTo(2);
        }

        @Test
        void testGetTemplateById() {
                TemplateCreateRequest request = new TemplateCreateRequest();
                request.setName("SMS Template to Get");
                request.setType(NotificationType.SMS);
                request.setMessage("Message to get");
                request.setDescription("Description to get");

                Map<String, Object> responseBody = webTestClient.post()
                                .uri("/api/v1/templates")
                                .header("X-Service-Token", serviceApp.getToken().toString())
                                .bodyValue(request)
                                .exchange()
                                .expectStatus().isCreated()
                                .expectBody(Map.class)
                                .returnResult()
                                .getResponseBody();

                Integer templateId = (Integer) responseBody.get("templateId");

                webTestClient.get()
                                .uri("/api/v1/templates/{id}?type={type}", templateId, NotificationType.SMS)
                                .header("X-Service-Token", serviceApp.getToken().toString())
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody()
                                .jsonPath("$.name").isEqualTo("SMS Template to Get")
                                .jsonPath("$.message").isEqualTo("Message to get");
        }

        @Test
        void testGetTemplateById_Unauthorized() {
                TemplateCreateRequest request = new TemplateCreateRequest();
                request.setName("SMS Template to Get");
                request.setType(NotificationType.SMS);
                request.setMessage("Message to get");
                request.setDescription("Description to get");

                Map<String, Object> responseBody = webTestClient.post()
                                .uri("/api/v1/templates")
                                .header("X-Service-Token", serviceApp.getToken().toString())
                                .bodyValue(request)
                                .exchange()
                                .expectStatus().isCreated()
                                .expectBody(Map.class)
                                .returnResult()
                                .getResponseBody();

                Integer templateId = (Integer) responseBody.get("templateId");

                // Create a new service to simulate an unauthorized request
                ServiceApp otherApp = new ServiceApp();
                otherApp.setName("Other App");
                otherApp.setToken(UUID.randomUUID());
                serviceAppRepository.save(otherApp).block();

                webTestClient.get()
                                .uri("/api/v1/templates/{id}?type={type}", templateId, NotificationType.SMS)
                                .header("X-Service-Token", otherApp.getToken().toString())
                                .exchange()
                                .expectStatus().isUnauthorized();
        }

        @Test
        void testPatchTemplate() {
                TemplateCreateRequest createRequest = new TemplateCreateRequest();
                createRequest.setName("Original SMS Template");
                createRequest.setType(NotificationType.SMS);
                createRequest.setMessage("Original Message");
                createRequest.setDescription("Original Description");

                Map<String, Object> responseBody = webTestClient.post()
                                .uri("/api/v1/templates")
                                .header("X-Service-Token", serviceApp.getToken().toString())
                                .bodyValue(createRequest)
                                .exchange()
                                .expectStatus().isCreated()
                                .expectBody(Map.class)
                                .returnResult()
                                .getResponseBody();

                Integer templateId = (Integer) responseBody.get("templateId");

                TemplateCreateRequest updateRequest = new TemplateCreateRequest();
                updateRequest.setName("Updated SMS Template");
                updateRequest.setMessage("Updated Message");
                updateRequest.setDescription("Updated Description");

                webTestClient.patch()
                                .uri("/api/v1/templates/{id}?type={type}", templateId, NotificationType.SMS)
                                .header("X-Service-Token", serviceApp.getToken().toString())
                                .bodyValue(updateRequest)
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody()
                                .jsonPath("$.name").isEqualTo("Updated SMS Template")
                                .jsonPath("$.message").isEqualTo("Updated Message");

                webTestClient.get()
                                .uri("/api/v1/templates/{id}?type={type}", templateId, NotificationType.SMS)
                                .header("X-Service-Token", serviceApp.getToken().toString())
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody()
                                .jsonPath("$.name").isEqualTo("Updated SMS Template");
        }

        @Test
        void testDeleteTemplate() {
                TemplateCreateRequest request = new TemplateCreateRequest();
                request.setName("SMS Template to Delete");
                request.setType(NotificationType.SMS);
                request.setMessage("Message to delete");
                request.setDescription("Description to delete");

                Map<String, Object> responseBody = webTestClient.post()
                                .uri("/api/v1/templates")
                                .header("X-Service-Token", serviceApp.getToken().toString())
                                .bodyValue(request)
                                .exchange()
                                .expectStatus().isCreated()
                                .expectBody(Map.class)
                                .returnResult()
                                .getResponseBody();

                Integer templateId = (Integer) responseBody.get("templateId");

                webTestClient.delete()
                                .uri("/api/v1/templates/{id}?type={type}", templateId, NotificationType.SMS)
                                .header("X-Service-Token", serviceApp.getToken().toString())
                                .exchange()
                                .expectStatus().isNoContent();

                webTestClient.get()
                                .uri("/api/v1/templates/{id}?type={type}", templateId, NotificationType.SMS)
                                .header("X-Service-Token", serviceApp.getToken().toString())
                                .exchange()
                                .expectStatus().isNotFound();
        }
}
