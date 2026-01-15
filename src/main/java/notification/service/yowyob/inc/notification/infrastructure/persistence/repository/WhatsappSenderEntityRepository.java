package notification.service.yowyob.inc.notification.infrastructure.persistence.repository;

import notification.service.yowyob.inc.notification.infrastructure.persistence.entity.WhatsappSenderEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface WhatsappSenderEntityRepository extends ReactiveCrudRepository<WhatsappSenderEntity, Integer> {

    Mono<WhatsappSenderEntity> findByServiceAppId(Integer serviceAppId);
}
