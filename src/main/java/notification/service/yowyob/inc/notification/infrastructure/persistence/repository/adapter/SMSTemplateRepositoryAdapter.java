package notification.service.yowyob.inc.notification.infrastructure.persistence.repository.adapter;

import lombok.AllArgsConstructor;
import notification.service.yowyob.inc.notification.application.domain.model.SMSTemplate;
import notification.service.yowyob.inc.notification.application.domain.repository.SMSTemplateRepository;
import notification.service.yowyob.inc.notification.infrastructure.persistence.entity.SMSTemplateEntity;
import notification.service.yowyob.inc.notification.infrastructure.persistence.repository.SMSTemplateEntityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SMSTemplateRepositoryAdapter implements SMSTemplateRepository {

  private final SMSTemplateEntityRepository smsTemplateEntityRepository;
  private final ModelMapper modelMapper;

  @Override
  public SMSTemplate save(SMSTemplate smsTemplate) {
    SMSTemplateEntity entity = toEntity(smsTemplate);
    return modelMapper.map(
        smsTemplateEntityRepository.save(entity).block(), SMSTemplate.class);
  }

  private SMSTemplate toDomainObject(SMSTemplateEntity entity) {
    return modelMapper.map(entity, SMSTemplate.class);
  }

  private SMSTemplateEntity toEntity(SMSTemplate domainObject) {
    return modelMapper.map(domainObject, SMSTemplateEntity.class);
  }
}