package notification.service.yowyob.inc.notification.infrastructure.persistence.repository.adapter;

import notification.service.yowyob.inc.notification.application.domain.model.ServiceApp;
import notification.service.yowyob.inc.notification.application.domain.model.WhatsappTemplate;
import notification.service.yowyob.inc.notification.application.domain.repository.WhatsappTemplateRepository;
import notification.service.yowyob.inc.notification.infrastructure.persistence.entity.WhatsappTemplateEntity;
import notification.service.yowyob.inc.notification.infrastructure.persistence.repository.WhatsappTemplateEntityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
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
    public Mono<WhatsappTemplate> findById(int id) {
        return whatsappTemplateEntityRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public Mono<WhatsappTemplate> findByServiceApp(ServiceApp serviceApp) {
        return whatsappTemplateEntityRepository.findByServiceAppId(serviceApp.getServiceId())
                .map(this::toDomain);
    }

    @Override
    public Flux<WhatsappTemplate> findAllByServiceAppId(Integer serviceAppId) {
        return whatsappTemplateEntityRepository.findAllByServiceAppId(serviceAppId)
                .map(this::toDomain);
    }

    @Override
    public Mono<Void> deleteById(Integer id) {
        return whatsappTemplateEntityRepository.deleteById(id);
    }

    @Override
    public Mono<WhatsappTemplate> findById(Integer id) {
        return whatsappTemplateEntityRepository.findById(id)
                .map(this::toDomain);
    }

    private WhatsappTemplate toDomain(WhatsappTemplateEntity entity) {
        return modelMapper.map(entity, WhatsappTemplate.class);
    }

    private WhatsappTemplateEntity toEntity(WhatsappTemplate domain) {
        return modelMapper.map(domain, WhatsappTemplateEntity.class);
    }
}
