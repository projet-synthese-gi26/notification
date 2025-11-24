package notification.service.yowyob.inc.notification.infrastructure.persistence.repository.adapter;

import lombok.AllArgsConstructor;
import notification.service.yowyob.inc.notification.application.domain.model.ServiceApp;
import notification.service.yowyob.inc.notification.application.domain.repository.ServiceAppRepository;
import notification.service.yowyob.inc.notification.infrastructure.persistence.entity.ServiceAppEntity;
import notification.service.yowyob.inc.notification.infrastructure.persistence.repository.ServiceAppEntityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.UUID;

@Component
@AllArgsConstructor
public class ServiceAppRepositoryAdapter implements ServiceAppRepository {

  private final ServiceAppEntityRepository serviceAppEntityRepository;
  private final ModelMapper modelMapper;

  @Override
  public ServiceApp save(ServiceApp serviceApp) {
    ServiceAppEntity entity = toEntity(serviceApp);
    return modelMapper.map(
        serviceAppEntityRepository.save(entity).block(), ServiceApp.class);
  }

  @Override
  public ServiceApp findByToken(UUID token) {
    return serviceAppEntityRepository.findByToken(token)
        .map(this::toDomainObject).blockOptional()
        .orElseThrow(() -> new NoSuchElementException("ServiceApp not found with token: " + token));
  }

  private ServiceApp toDomainObject(ServiceAppEntity entity) {
    return modelMapper.map(entity, ServiceApp.class);
  }

  private ServiceAppEntity toEntity(ServiceApp domainObject) {
    return modelMapper.map(domainObject, ServiceAppEntity.class);
  }
}