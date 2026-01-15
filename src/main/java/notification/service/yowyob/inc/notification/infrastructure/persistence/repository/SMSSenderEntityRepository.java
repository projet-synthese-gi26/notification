package notification.service.yowyob.inc.notification.infrastructure.persistence.repository;

import notification.service.yowyob.inc.notification.infrastructure.persistence.entity.SMSSenderEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface SMSSenderEntityRepository extends ReactiveCrudRepository<SMSSenderEntity, Integer> {
  Mono<SMSSenderEntity> findByServiceAppId(Integer serviceAppId);
}