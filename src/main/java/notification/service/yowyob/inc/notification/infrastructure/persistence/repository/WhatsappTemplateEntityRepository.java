package notification.service.yowyob.inc.notification.infrastructure.persistence.repository;

import notification.service.yowyob.inc.notification.infrastructure.persistence.entity.WhatsappTemplateEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface WhatsappTemplateEntityRepository extends ReactiveCrudRepository<WhatsappTemplateEntity, Integer> {

    Mono<WhatsappTemplateEntity> findByServiceAppId(Integer serviceAppId);
    Mono<WhatsappTemplateEntity> findById(Integer id);
    Flux<WhatsappTemplateEntity> findAllByServiceAppId(Integer serviceAppId);
}
