package notification.service.yowyob.inc.notification.infrastructure.persistence.repository;

import notification.service.yowyob.inc.notification.infrastructure.persistence.entity.EmailTemplateEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface EmailTemplateEntityRepository extends ReactiveCrudRepository<EmailTemplateEntity, Integer> {
    Mono<EmailTemplateEntity> findByServiceAppId(Integer serviceAppId);
    Mono<EmailTemplateEntity> findById(Integer id);
    Flux<EmailTemplateEntity> findAllByServiceAppId(Integer serviceAppId);
}