package notification.service.yowyob.inc.notification.infrastructure.persistence.repository.adapter;

import lombok.AllArgsConstructor;
import notification.service.yowyob.inc.notification.application.domain.model.ServiceApp;
import notification.service.yowyob.inc.notification.application.domain.repository.ServiceAppRepository;
import notification.service.yowyob.inc.notification.infrastructure.persistence.entity.ServiceAppEntity;
import notification.service.yowyob.inc.notification.infrastructure.persistence.repository.ServiceAppEntityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@AllArgsConstructor
public class ServiceAppRepositoryAdapter implements ServiceAppRepository {

  private final ServiceAppEntityRepository serviceAppEntityRepository;
  private final ModelMapper modelMapper;

  @Override
  public Mono<ServiceApp> save(ServiceApp serviceApp) {
    ServiceAppEntity entity = toEntity(serviceApp);
    return serviceAppEntityRepository.save(entity)
            .map(this::toDomainObject);
  }

  @Override
  public Mono<ServiceApp> findByToken(UUID token) {
    return serviceAppEntityRepository.findByToken(token)
        .map(this::toDomainObject);
  }

  private ServiceApp toDomainObject(ServiceAppEntity entity) {
    return modelMapper.map(entity, ServiceApp.class);
  }

  private ServiceAppEntity toEntity(ServiceApp domainObject) {
    return modelMapper.map(domainObject, ServiceAppEntity.class);
  }
}