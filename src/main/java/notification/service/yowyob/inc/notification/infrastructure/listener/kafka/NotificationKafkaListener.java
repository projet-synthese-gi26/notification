package notification.service.yowyob.inc.notification.infrastructure.listener.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import notification.service.yowyob.inc.notification.application.domain.service.NotificationService;
import notification.service.yowyob.inc.notification.application.port.input.dto.NotificationCreateRequest;
import notification.service.yowyob.inc.notification.application.port.input.dto.NotificationSendRequest;

import static notification.service.yowyob.inc.notification.infrastructure.listener.kafka.KafkaTopicConfig.NOTIFICATION_CREATE_TOPIC;
import static notification.service.yowyob.inc.notification.infrastructure.listener.kafka.KafkaTopicConfig.NOTIFICATION_SEND_TOPIC;

@Component
@AllArgsConstructor
@Log4j2
public class NotificationKafkaListener {

    private final NotificationService notificationService;

    @KafkaListener(topics = NOTIFICATION_SEND_TOPIC, groupId = "${spring.kafka.consumer.group-id}")
    public void listenNotificationSend(
            @Header("X-Service-Token") String token,
            @Payload NotificationSendRequest request) {
        try {
            log.info("Received notification send request: {}", request);
            notificationService.send(
                    token,
                    request.getNotificationType(),
                    request.getTemplateId(),
                    request.getTo(),
                    request.getData());
            log.info("Notification sent successfully for request: {}", request);
        } catch (Exception e) {
            log.error("Error processing notification send request: {}", request, e);
        }
    }

    @KafkaListener(topics = NOTIFICATION_CREATE_TOPIC, groupId = "${spring.kafka.consumer.group-id}")
    public void listenNotificationCreate(
            @Header("X-Service-Token") String token,
            @Payload NotificationCreateRequest request) {
        try {
            log.info("Received notification create request: {}", request);
            notificationService.create(
                    token,
                    request.getNotificationType(),
                    request.getTemplateId(),
                    request.getUserId(),
                    request.getData());
            log.info("Notification created successfully for request: {}", request);
        } catch (Exception e) {
            log.error("Error processing notification create request: {}", request, e);
        }
    }
}
