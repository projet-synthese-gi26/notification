package notification.service.yowyob.inc.notification.infrastructure.persistence.repository.adapter;

import lombok.AllArgsConstructor;
import notification.service.yowyob.inc.notification.application.domain.model.EmailSender;
import notification.service.yowyob.inc.notification.application.domain.model.ServiceApp;
import notification.service.yowyob.inc.notification.application.domain.repository.EmailSenderRepository;
import notification.service.yowyob.inc.notification.infrastructure.persistence.entity.EmailSenderEntity;
import notification.service.yowyob.inc.notification.infrastructure.persistence.repository.EmailSenderEntityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@AllArgsConstructor
public class EmailSenderRepositoryAdapter implements EmailSenderRepository {

  private final EmailSenderEntityRepository emailSenderEntityRepository;
  private final ModelMapper modelMapper;

  @Override
  public EmailSender save(EmailSender emailSender) {
    EmailSenderEntity entity = toEntity(emailSender);
    return modelMapper.map(
        emailSenderEntityRepository.save(entity).block(), EmailSender.class);
  }

  @Override
  public EmailSender findByServiceApp(ServiceApp serviceApp) {
    return emailSenderEntityRepository.findByServiceAppId(serviceApp.getServiceId())
        .map(this::toDomainObject).blockOptional()
        .orElseThrow(
            () -> new NoSuchElementException("EmailSender not found for the given service app"));
  }

  private EmailSender toDomainObject(EmailSenderEntity entity) {
    return modelMapper.map(entity, EmailSender.class);
  }

  private EmailSenderEntity toEntity(EmailSender domainObject) {
    return modelMapper.map(domainObject, EmailSenderEntity.class);
  }
}