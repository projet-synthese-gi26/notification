package notification.service.yowyob.inc.notification.infrastructure.listener.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class notificationEventConsumer {

  @KafkaListener(topics = "send-notification-topic", groupId = "notification")
  public void consume() {

  }
}
