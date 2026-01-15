package notification.service.yowyob.inc.notification.infrastructure.persistence.repository.adapter;

import lombok.AllArgsConstructor;
import notification.service.yowyob.inc.notification.application.domain.model.ServiceApp;
import notification.service.yowyob.inc.notification.application.domain.model.WhatsappTemplate;
import notification.service.yowyob.inc.notification.application.domain.repository.WhatsappTemplateRepository;
import notification.service.yowyob.inc.notification.infrastructure.persistence.entity.WhatsappTemplateEntity;
import notification.service.yowyob.inc.notification.infrastructure.persistence.repository.WhatsappTemplateEntityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class WhatsappTemplateRepositoryAdapter implements WhatsappTemplateRepository {

    private final WhatsappTemplateEntityRepository whatsappTemplateEntityRepository;
    private final ModelMapper modelMapper;

    @Override
    public Mono<WhatsappTemplate> save(WhatsappTemplate whatsappTemplate) {
        WhatsappTemplateEntity entity = toEntity(whatsappTemplate);
        return whatsappTemplateEntityRepository.save(entity)
                .map(this::toDomain);
    }

    @Override
    public Mono<WhatsappTemplate> findByServiceApp(ServiceApp serviceApp) {
        return whatsappTemplateEntityRepository.findByServiceAppId(serviceApp.getServiceId())
                .map(this::toDomain);
    }

    private WhatsappTemplate toDomain(WhatsappTemplateEntity entity) {
        return modelMapper.map(entity, WhatsappTemplate.class);
    }

    private WhatsappTemplateEntity toEntity(WhatsappTemplate domain) {
        return modelMapper.map(domain, WhatsappTemplateEntity.class);
    }
}
