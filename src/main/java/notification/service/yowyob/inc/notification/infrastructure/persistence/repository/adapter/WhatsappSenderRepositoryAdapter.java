package notification.service.yowyob.inc.notification.infrastructure.persistence.repository.adapter;

import lombok.AllArgsConstructor;
import notification.service.yowyob.inc.notification.application.domain.model.ServiceApp;
import notification.service.yowyob.inc.notification.application.domain.model.WhatsappSender;
import notification.service.yowyob.inc.notification.application.domain.repository.WhatsappSenderRepository;
import notification.service.yowyob.inc.notification.infrastructure.persistence.entity.WhatsappSenderEntity;
import notification.service.yowyob.inc.notification.infrastructure.persistence.repository.WhatsappSenderEntityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class WhatsappSenderRepositoryAdapter implements WhatsappSenderRepository {

    private final WhatsappSenderEntityRepository whatsappSenderEntityRepository;
    private final ModelMapper modelMapper;

    @Override
    public Mono<WhatsappSender> save(WhatsappSender whatsappSender) {
        WhatsappSenderEntity entity = toEntity(whatsappSender);
        return whatsappSenderEntityRepository.save(entity)
                .map(this::toDomain);
    }

    @Override
    public Mono<WhatsappSender> findByServiceApp(ServiceApp serviceApp) {
        return whatsappSenderEntityRepository.findByServiceAppId(serviceApp.getServiceId())
                .map(this::toDomain);
    }

    @Override
    public Mono<WhatsappSender> findByServiceAppId(Integer serviceAppId) {
        return whatsappSenderEntityRepository.findByServiceAppId(serviceAppId)
                .map(this::toDomain);
    }

    private WhatsappSender toDomain(WhatsappSenderEntity entity) {
        return modelMapper.map(entity, WhatsappSender.class);
    }

    private WhatsappSenderEntity toEntity(WhatsappSender domain) {
        return modelMapper.map(domain, WhatsappSenderEntity.class);
    }
}
