package notification.service.yowyob.inc.notification.infrastructure.listener.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import notification.service.yowyob.inc.notification.application.domain.model.Template;
import notification.service.yowyob.inc.notification.application.domain.service.TemplateFactory;
import notification.service.yowyob.inc.notification.application.port.input.dto.TemplateCreateRequest;

import static notification.service.yowyob.inc.notification.infrastructure.listener.kafka.KafkaTopicConfig.TEMPLATE_CREATE_TOPIC;

@Component
@AllArgsConstructor
@Log4j2
public class TemplateKafkaListener {

    private final TemplateFactory templateFactory;

    @KafkaListener(topics = TEMPLATE_CREATE_TOPIC, groupId = "${spring.kafka.consumer.group-id}")
    public void listenTemplateCreate(
            @Header("X-Service-Token") String token,
            @Payload TemplateCreateRequest request) {
        try {
            log.info("Received template create request: {}", request);
            Template createdTemplate = templateFactory.createTemplate(token, request);
            log.info("Template created successfully with ID: {}", createdTemplate.getTemplateId());
        } catch (Exception e) {
            log.error("Error processing template create request: {}", request, e);
        }
    }
}
