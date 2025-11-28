package notification.service.yowyob.inc.notification.infrastructure.listener.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import notification.service.yowyob.inc.notification.application.domain.service.ServiceAppService;
import notification.service.yowyob.inc.notification.application.port.input.dto.ServiceCreateRequest;
import notification.service.yowyob.inc.notification.application.port.output.dto.ServiceCreateResponse;
import static notification.service.yowyob.inc.notification.infrastructure.listener.kafka.KafkaTopicConfig.SERVICE_REGISTRATION_TOPIC;


@Component
@AllArgsConstructor
@Log4j2
public class ServiceAppKafkaListener {

    private final ServiceAppService serviceAppService;

    @KafkaListener(topics = SERVICE_REGISTRATION_TOPIC, groupId = "${spring.kafka.consumer.group-id}")
    public void listenServiceRegistration(ServiceCreateRequest request) {
        try {
            log.info("Received service registration request: {}", request);
            ServiceCreateResponse response = serviceAppService.registerServiceApp(request);
            log.info("Service registered successfully with ID: {}", response.getServiceId());
        } catch (Exception e) {
            log.error("Error processing service registration request: {}", request, e);
        }
    }
}
