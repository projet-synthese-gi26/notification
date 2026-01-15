package notification.service.yowyob.inc.notification.infrastructure.persistence.repository;

import notification.service.yowyob.inc.notification.infrastructure.persistence.entity.PullTemplateEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PullTemplateEntityRepository extends ReactiveCrudRepository<PullTemplateEntity, Integer> {
    Mono<PullTemplateEntity> findByServiceAppId(Integer serviceAppId);
    Mono<PullTemplateEntity> findById(Integer id);
}