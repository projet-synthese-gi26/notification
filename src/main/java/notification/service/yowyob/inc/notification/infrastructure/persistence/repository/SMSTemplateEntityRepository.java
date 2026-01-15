package notification.service.yowyob.inc.notification.infrastructure.persistence.repository;

import notification.service.yowyob.inc.notification.infrastructure.persistence.entity.SMSTemplateEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface SMSTemplateEntityRepository extends ReactiveCrudRepository<SMSTemplateEntity, Integer> {
    Mono<SMSTemplateEntity> findByServiceAppId(Integer serviceAppId);
    Mono<SMSTemplateEntity> findById(Integer id);
}