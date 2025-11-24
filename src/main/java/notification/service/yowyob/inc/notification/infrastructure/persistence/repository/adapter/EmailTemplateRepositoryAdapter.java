package notification.service.yowyob.inc.notification.infrastructure.persistence.repository.adapter;

import lombok.AllArgsConstructor;
import notification.service.yowyob.inc.notification.application.domain.model.EmailTemplate;
import notification.service.yowyob.inc.notification.application.domain.repository.EmailTemplateRepository;
import notification.service.yowyob.inc.notification.infrastructure.persistence.entity.EmailTemplateEntity;
import notification.service.yowyob.inc.notification.infrastructure.persistence.repository.EmailTemplateEntityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@AllArgsConstructor
public class EmailTemplateRepositoryAdapter implements EmailTemplateRepository {

  private final EmailTemplateEntityRepository emailTemplateEntityRepository;
  private final ModelMapper modelMapper;

  @Override
  public EmailTemplate save(EmailTemplate emailTemplate) {
    EmailTemplateEntity entity = toEntity(emailTemplate);
    return modelMapper.map(
        emailTemplateEntityRepository.save(entity).block(), EmailTemplate.class);
  }

  @Override
  public EmailTemplate findById(int id) {
    return emailTemplateEntityRepository.findById(id)
        .map(this::toDomainObject).blockOptional()
        .orElseThrow(() -> new NoSuchElementException("EmailTemplate not found with id: " + id));
  }

  private EmailTemplate toDomainObject(EmailTemplateEntity entity) {
    return modelMapper.map(entity, EmailTemplate.class);
  }

  private EmailTemplateEntity toEntity(EmailTemplate domainObject) {
    return modelMapper.map(domainObject, EmailTemplateEntity.class);
  }
}