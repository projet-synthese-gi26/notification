package notification.service.yowyob.inc.notification.infrastructure.persistence.repository.adapter;

import lombok.AllArgsConstructor;
import notification.service.yowyob.inc.notification.application.domain.model.PullTemplate;
import notification.service.yowyob.inc.notification.application.domain.repository.PullTemplateRepository;
import notification.service.yowyob.inc.notification.infrastructure.persistence.entity.PullTemplateEntity;
import notification.service.yowyob.inc.notification.infrastructure.persistence.repository.PullTemplateEntityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PullTemplateRepositoryAdapter implements PullTemplateRepository {

  private final PullTemplateEntityRepository pullTemplateEntityRepository;
  private final ModelMapper modelMapper;

  @Override
  public PullTemplate save(PullTemplate pullTemplate) {
    PullTemplateEntity entity = toEntity(pullTemplate);
    return modelMapper.map(
        pullTemplateEntityRepository.save(entity).block(), PullTemplate.class);
  }

  private PullTemplate toDomainObject(PullTemplateEntity entity) {
    return modelMapper.map(entity, PullTemplate.class);
  }

  private PullTemplateEntity toEntity(PullTemplate domainObject) {
    return modelMapper.map(domainObject, PullTemplateEntity.class);
  }
}