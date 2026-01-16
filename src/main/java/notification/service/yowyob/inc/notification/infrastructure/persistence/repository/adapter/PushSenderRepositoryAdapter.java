package notification.service.yowyob.inc.notification.infrastructure.persistence.repository.adapter;

import lombok.AllArgsConstructor;
import notification.service.yowyob.inc.notification.application.domain.model.PushSender;
import notification.service.yowyob.inc.notification.application.domain.model.ServiceApp;
import notification.service.yowyob.inc.notification.application.domain.repository.PushSenderRepository;
import reactor.core.publisher.Mono;
import org.springframework.stereotype.Component;
import org.modelmapper.ModelMapper;
import notification.service.yowyob.inc.notification.infrastructure.persistence.entity.PushSenderEntity;
import notification.service.yowyob.inc.notification.infrastructure.persistence.repository.PushSenderEntityRepository;

@Component
@AllArgsConstructor
public class PushSenderRepositoryAdapter implements PushSenderRepository {

  private final PushSenderEntityRepository pushSenderEntityRepository;
  private final ModelMapper modelMapper;

  @Override
  public Mono<PushSender> save(PushSender pushSender) {
    PushSenderEntity entity = toEntity(pushSender);
    return pushSenderEntityRepository.save(entity)
        .map(this::toDomainObject);
  }

  @Override
  public Mono<PushSender> findByServiceApp(ServiceApp serviceApp) {
    return pushSenderEntityRepository.findByServiceAppId(serviceApp.getServiceId())
        .map(this::toDomainObject);
  }

  @Override
  public Mono<PushSender> findByServiceAppId(Integer serviceAppId) {
    return pushSenderEntityRepository.findByServiceAppId(serviceAppId)
        .map(this::toDomainObject);
  }

  private PushSender toDomainObject(PushSenderEntity entity) {
    return modelMapper.map(entity, PushSender.class);
  }

  private PushSenderEntity toEntity(PushSender domainObject) {
    return modelMapper.map(domainObject, PushSenderEntity.class);
  }
}
