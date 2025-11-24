package notification.service.yowyob.inc.notification.infrastructure.persistence.repository.adapter;

import lombok.AllArgsConstructor;
import notification.service.yowyob.inc.notification.application.domain.model.SMSSender;
import notification.service.yowyob.inc.notification.application.domain.repository.SMSSenderRepository;
import notification.service.yowyob.inc.notification.infrastructure.persistence.entity.SMSSenderEntity;
import notification.service.yowyob.inc.notification.infrastructure.persistence.repository.SMSSenderEntityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SMSSenderRepositoryAdapter implements SMSSenderRepository {

  private final SMSSenderEntityRepository smsSenderEntityRepository;
  private final ModelMapper modelMapper;

  @Override
  public SMSSender save(SMSSender smsSender) {
    SMSSenderEntity entity = toEntity(smsSender);
    return modelMapper.map(
        smsSenderEntityRepository.save(entity).block(), SMSSender.class);
  }

  private SMSSender toDomainObject(SMSSenderEntity entity) {
    return modelMapper.map(entity, SMSSender.class);
  }

  private SMSSenderEntity toEntity(SMSSender domainObject) {
    return modelMapper.map(domainObject, SMSSenderEntity.class);
  }
}