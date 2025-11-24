package notification.service.yowyob.inc.notification.infrastructure.persistence.repository;

import notification.service.yowyob.inc.notification.infrastructure.persistence.entity.EmailSenderEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface EmailSenderEntityRepository extends ReactiveCrudRepository<EmailSenderEntity, Integer> {
  Mono<EmailSenderEntity> findByServiceAppId(Integer serviceAppId);
}