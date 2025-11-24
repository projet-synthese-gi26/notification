package notification.service.yowyob.inc.notification.infrastructure.persistence.repository;

import notification.service.yowyob.inc.notification.infrastructure.persistence.entity.ServiceAppEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface ServiceAppEntityRepository extends ReactiveCrudRepository<ServiceAppEntity, Integer> {
  Mono<ServiceAppEntity> findByToken(UUID token);
}