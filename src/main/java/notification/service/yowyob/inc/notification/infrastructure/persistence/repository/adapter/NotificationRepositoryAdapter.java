package notification.service.yowyob.inc.notification.infrastructure.persistence.repository.adapter;

import lombok.AllArgsConstructor;
import notification.service.yowyob.inc.notification.application.domain.model.Notification;
import notification.service.yowyob.inc.notification.application.domain.repository.NotificationRepository;
import notification.service.yowyob.inc.notification.infrastructure.persistence.entity.NotificationEntity;
import notification.service.yowyob.inc.notification.infrastructure.persistence.repository.NotificationEntityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class NotificationRepositoryAdapter implements NotificationRepository {

  private final NotificationEntityRepository notificationEntityRepository;
  private final ModelMapper modelMapper;

  @Override
  public Notification save(Notification notification) {
    NotificationEntity entity = toEntity(notification);
    NotificationEntity savedEntity = notificationEntityRepository.save(entity);
    return toDomainObject(savedEntity);
  }

  private Notification toDomainObject(NotificationEntity entity) {
    return modelMapper.map(entity, Notification.class);
  }

  private NotificationEntity toEntity(Notification domainObject) {
    return modelMapper.map(domainObject, NotificationEntity.class);
  }
}