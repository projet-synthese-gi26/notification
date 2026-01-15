package notification.service.yowyob.inc.notification.infrastructure.persistence.repository;

import notification.service.yowyob.inc.notification.infrastructure.persistence.entity.PushSenderEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PushSenderEntityRepository extends ReactiveCrudRepository<PushSenderEntity, Integer> {
  Mono<PushSenderEntity> findByServiceAppId(Integer serviceAppId);
}
