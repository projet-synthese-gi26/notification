package notification.service.yowyob.inc.notification.infrastructure.persistence.repository;

import notification.service.yowyob.inc.notification.infrastructure.persistence.entity.PushTemplateEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PushTemplateEntityRepository extends ReactiveCrudRepository<PushTemplateEntity, Integer> {
    Mono<PushTemplateEntity> findByServiceAppId(Integer serviceAppId);
    Mono<PushTemplateEntity> findById(Integer id);
}
