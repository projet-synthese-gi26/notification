package notification.service.yowyob.inc.notification.infrastructure.persistence.repository.adapter;

import lombok.AllArgsConstructor;
import notification.service.yowyob.inc.notification.application.domain.model.EmailSender;
import notification.service.yowyob.inc.notification.application.domain.model.ServiceApp;
import notification.service.yowyob.inc.notification.application.domain.repository.EmailSenderRepository;
import notification.service.yowyob.inc.notification.infrastructure.persistence.entity.EmailSenderEntity;
import notification.service.yowyob.inc.notification.infrastructure.persistence.repository.EmailSenderEntityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class EmailSenderRepositoryAdapter implements EmailSenderRepository {

  private final EmailSenderEntityRepository emailSenderEntityRepository;
  private final ModelMapper modelMapper;

  @Override
  public Mono<EmailSender> save(EmailSender emailSender) {
    EmailSenderEntity entity = toEntity(emailSender);
    return emailSenderEntityRepository.save(entity)
        .map(this::toDomainObject);
  }

  @Override
  public Mono<EmailSender> findByServiceApp(ServiceApp serviceApp) {
    return emailSenderEntityRepository.findByServiceAppId(serviceApp.getServiceId())
        .map(this::toDomainObject);
  }

  @Override
  public Mono<EmailSender> findByServiceAppId(Integer serviceAppId) {
    return emailSenderEntityRepository.findByServiceAppId(serviceAppId)
        .map(this::toDomainObject);
  }

  private EmailSender toDomainObject(EmailSenderEntity entity) {
    return modelMapper.map(entity, EmailSender.class);
  }

  private EmailSenderEntity toEntity(EmailSender domainObject) {
    return modelMapper.map(domainObject, EmailSenderEntity.class);
  }
}