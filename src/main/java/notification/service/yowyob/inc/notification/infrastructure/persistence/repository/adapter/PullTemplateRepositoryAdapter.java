package notification.service.yowyob.inc.notification.infrastructure.persistence.repository.adapter;

import lombok.AllArgsConstructor;
import notification.service.yowyob.inc.notification.application.domain.model.PullTemplate;
import notification.service.yowyob.inc.notification.application.domain.model.ServiceApp;
import notification.service.yowyob.inc.notification.application.domain.repository.PullTemplateRepository;
import notification.service.yowyob.inc.notification.infrastructure.persistence.entity.PullTemplateEntity;
import notification.service.yowyob.inc.notification.infrastructure.persistence.repository.PullTemplateEntityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class PullTemplateRepositoryAdapter implements PullTemplateRepository {

  private final PullTemplateEntityRepository pullTemplateEntityRepository;
  private final ModelMapper modelMapper;

  @Override
  public Mono<PullTemplate> save(PullTemplate pullTemplate) {
    PullTemplateEntity entity = toEntity(pullTemplate);
    return pullTemplateEntityRepository.save(entity)
        .map(this::toDomainObject);
  }

  @Override
  public Mono<PullTemplate> findById(int id) {
    return pullTemplateEntityRepository.findById(id)
        .map(this::toDomainObject);
  }

  @Override
  public Mono<PullTemplate> findByServiceApp(ServiceApp serviceApp) {
    return pullTemplateEntityRepository.findByServiceAppId(serviceApp.getServiceId())
        .map(this::toDomainObject);
  }

  private PullTemplate toDomainObject(PullTemplateEntity entity) {
    return modelMapper.map(entity, PullTemplate.class);
  }

  private PullTemplateEntity toEntity(PullTemplate domainObject) {
    return modelMapper.map(domainObject, PullTemplateEntity.class);
  }
}