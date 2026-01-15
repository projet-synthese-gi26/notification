package notification.service.yowyob.inc.notification;

import notification.service.yowyob.inc.notification.application.domain.enums.NotificationType;
import notification.service.yowyob.inc.notification.application.domain.model.EmailSender;
import notification.service.yowyob.inc.notification.application.domain.model.EmailTemplate;
import notification.service.yowyob.inc.notification.application.domain.model.ServiceApp;
import notification.service.yowyob.inc.notification.application.domain.model.Template;
import notification.service.yowyob.inc.notification.application.domain.model.WhatsappSender;
import notification.service.yowyob.inc.notification.application.domain.model.WhatsappTemplate;
import notification.service.yowyob.inc.notification.application.domain.repository.ServiceAppRepository;
import notification.service.yowyob.inc.notification.application.domain.repository.WhatsappSenderRepository;
import notification.service.yowyob.inc.notification.application.domain.repository.WhatsappTemplateRepository;
import notification.service.yowyob.inc.notification.application.domain.repository.EmailSenderRepository;
import notification.service.yowyob.inc.notification.application.domain.repository.EmailTemplateRepository;
import notification.service.yowyob.inc.notification.application.port.input.dto.NotificationSendRequest;
import notification.service.yowyob.inc.notification.application.port.input.dto.NotificationCreateRequest;
import notification.service.yowyob.inc.notification.application.port.output.service.WhatsappSenderServiceInterface;
import notification.service.yowyob.inc.notification.application.port.output.service.EmailSenderServiceInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing; // Import doNothing

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class NotificationControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ServiceAppRepository serviceAppRepository;

    @Autowired
    private WhatsappSenderRepository whatsappSenderRepository;

    @Autowired
    private WhatsappTemplateRepository whatsappTemplateRepository;

    @Autowired
    private EmailSenderRepository emailSenderRepository;

    @Autowired
    private EmailTemplateRepository emailTemplateRepository;

    @MockBean
    private WhatsappSenderServiceInterface whatsappSenderServiceInterface;

    @MockBean
    private EmailSenderServiceInterface emailSenderServiceInterface;

    private ServiceApp serviceApp;
    private Template whatsappTemplate;
    private Template emailTemplate;


    @BeforeEach
    void setUp() {
        // Mock the external service
        when(whatsappSenderServiceInterface.sendWhatsappMessage(any(), any(), any(), any(), any()))
                .thenReturn(Mono.empty());
        doNothing().when(emailSenderServiceInterface).sendEmail(any(), any(), any(), any(), any(), any(), any(), any(), any()); // Corrected mocking

        // Create a service app
        ServiceApp app = new ServiceApp();
        app.setName("Test App");
        app.setToken(UUID.randomUUID());
        serviceApp = serviceAppRepository.save(app).block();

        // Create a whatsapp sender
        WhatsappSender sender = new WhatsappSender();
        sender.setServiceAppId(serviceApp.getServiceId());
        sender.setApiUrl("test.com");
        sender.setIdInstance("instance");
        sender.setApiTokenInstance("token");
        whatsappSenderRepository.save(sender).block();

        // Create an email sender
        EmailSender emailSender = new EmailSender();
        emailSender.setServiceAppId(serviceApp.getServiceId());
        emailSender.setServerHost("smtp.test.com");
        emailSender.setServerPort("587");
        emailSender.setUsername("testuser");
        emailSender.setPassword("testpassword");
        emailSenderRepository.save(emailSender).block();


        // Create a whatsapp template
        WhatsappTemplate template = new WhatsappTemplate();
        template.setServiceAppId(serviceApp.getServiceId());
        template.setName("Test Template");
        template.setBody("Hello {{name}}");
        template.setDescription("A test template");
        whatsappTemplate = whatsappTemplateRepository.save(template).block();

        // Create an email template
        EmailTemplate emailT = new EmailTemplate();
        emailT.setServiceAppId(serviceApp.getServiceId());
        emailT.setName("Test Email Template");
        emailT.setSubject("Hello {{name}}");
        emailT.setBodyHtml("Hello {{name}}, this is an email.");
        emailT.setDescription("A test email template");
        emailTemplate = emailTemplateRepository.save(emailT).block();

    }

    @Test
    void testSendWhatsappNotification() {
        NotificationSendRequest request = new NotificationSendRequest();
        request.setNotificationType(NotificationType.WHATSAPP);
        request.setTemplateId(whatsappTemplate.getTemplateId());
        request.setTo(List.of("1234567890"));
        request.setData(Map.of("name", "Test"));

        webTestClient.post()
                .uri("/api/v1/notifications/send")
                .header("X-Service-Token", serviceApp.getToken().toString())
                .bodyValue(request)
                .exchange()
                .expectStatus().isAccepted();
    }

    @Test
    void testCreatePullNotification() {
        NotificationCreateRequest request = new NotificationCreateRequest();
        request.setNotificationType(NotificationType.PULL);
        request.setTemplateId(whatsappTemplate.getTemplateId());
        request.setUserId(UUID.randomUUID());
        request.setData(Map.of("name", "Test"));

        webTestClient.post()
                .uri("/api/v1/notifications")
                .header("X-Service-Token", serviceApp.getToken().toString())
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.notificationType").isEqualTo("PULL")
                .jsonPath("$.userId").isEqualTo(request.getUserId().toString());
    }

    @Test
    void testCreateEmailNotification() {
        NotificationCreateRequest request = new NotificationCreateRequest();
        request.setNotificationType(NotificationType.EMAIL);
        request.setTemplateId(emailTemplate.getTemplateId());
        request.setUserId(UUID.randomUUID());
        request.setData(Map.of("name", "Test User"));

        webTestClient.post()
                .uri("/api/v1/notifications")
                .header("X-Service-Token", serviceApp.getToken().toString())
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.notificationType").isEqualTo("EMAIL")
                .jsonPath("$.userId").isEqualTo(request.getUserId().toString());
    }
}