package notification.service.yowyob.inc.notification.infrastructure.persistence.repository.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import notification.service.yowyob.inc.notification.application.domain.model.Notification;
import notification.service.yowyob.inc.notification.application.domain.repository.NotificationRepository;
import notification.service.yowyob.inc.notification.infrastructure.persistence.entity.NotificationEntity;
import notification.service.yowyob.inc.notification.infrastructure.persistence.repository.NotificationEntityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Map;

@Component
@AllArgsConstructor
public class NotificationRepositoryAdapter implements NotificationRepository {

  private final NotificationEntityRepository notificationEntityRepository;
  private final ModelMapper modelMapper;
  private final ObjectMapper objectMapper; // Inject ObjectMapper

  @Override
  public Mono<Notification> save(Notification notification) {
    NotificationEntity entity = toEntity(notification);
    return notificationEntityRepository.save(entity)
            .map(this::toDomainObject);
  }

  private Notification toDomainObject(NotificationEntity entity) {
    Notification notification = modelMapper.map(entity, Notification.class);
    if (entity.getData() != null) {
      try {
        notification.setData(objectMapper.readValue(entity.getData(), new TypeReference<Map<String, String>>() {}));
      } catch (IOException e) {
        throw new IllegalStateException("Error converting JSON string to Map in Notification", e);
      }
    }
    return notification;
  }

  private NotificationEntity toEntity(Notification domainObject) {
    NotificationEntity entity = modelMapper.map(domainObject, NotificationEntity.class);
    entity.setServiceAppId(domainObject.getServiceAppId());
    if (domainObject.getData() != null) {
      try {
        entity.setData(objectMapper.writeValueAsString(domainObject.getData()));
      } catch (JsonProcessingException e) {
        throw new IllegalStateException("Error converting Map to JSON string in NotificationEntity", e);
      }
    }
    return entity;
  }
}