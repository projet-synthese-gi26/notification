package notification.service.yowyob.inc.notification.infrastructure.persistence.repository.adapter;

import lombok.AllArgsConstructor;
import notification.service.yowyob.inc.notification.application.domain.model.PushTemplate;
import notification.service.yowyob.inc.notification.application.domain.repository.PushTemplateRepository;
import reactor.core.publisher.Mono;
import org.springframework.stereotype.Component;
import org.modelmapper.ModelMapper;
import notification.service.yowyob.inc.notification.infrastructure.persistence.entity.PushTemplateEntity;
import notification.service.yowyob.inc.notification.infrastructure.persistence.repository.PushTemplateEntityRepository;

@Component
@AllArgsConstructor
public class PushTemplateRepositoryAdapter implements PushTemplateRepository {

  private final PushTemplateEntityRepository pushTemplateEntityRepository;
  private final ModelMapper modelMapper;

  @Override
  public Mono<PushTemplate> findById(int id) {
    return pushTemplateEntityRepository.findById(id)
        .map(this::toDomainObject);
  }

  @Override
  public Mono<PushTemplate> save(PushTemplate pushTemplate) {
    PushTemplateEntity entity = toEntity(pushTemplate);
    return pushTemplateEntityRepository.save(entity)
        .map(this::toDomainObject);
  }

  private PushTemplate toDomainObject(PushTemplateEntity entity) {
    return modelMapper.map(entity, PushTemplate.class);
  }

  private PushTemplateEntity toEntity(PushTemplate domainObject) {
    return modelMapper.map(domainObject, PushTemplateEntity.class);
  }
}
