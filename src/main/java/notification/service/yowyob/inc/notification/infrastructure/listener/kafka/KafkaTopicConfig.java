package notification.service.yowyob.inc.notification.infrastructure.listener.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    public static final String SERVICE_REGISTRATION_TOPIC = "service-registration-topic";
    public static final String NOTIFICATION_SEND_TOPIC = "notification-send-topic";
    public static final String NOTIFICATION_CREATE_TOPIC = "notification-create-topic";
    public static final String TEMPLATE_CREATE_TOPIC = "template-create-topic";

    @Bean
    public NewTopic serviceRegistrationTopic() {
        return TopicBuilder.name(SERVICE_REGISTRATION_TOPIC)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic notificationSendTopic() {
        return TopicBuilder.name(NOTIFICATION_SEND_TOPIC)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic notificationCreateTopic() {
        return TopicBuilder.name(NOTIFICATION_CREATE_TOPIC)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic templateCreateTopic() {
        return TopicBuilder.name(TEMPLATE_CREATE_TOPIC)
                .partitions(1)
                .replicas(1)
                .build();
    }
}
