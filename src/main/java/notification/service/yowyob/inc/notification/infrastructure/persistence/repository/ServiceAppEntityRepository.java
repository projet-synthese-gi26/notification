package notification.service.yowyob.inc.notification.infrastructure.persistence.repository;

import notification.service.yowyob.inc.notification.infrastructure.persistence.entity.ServiceAppEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ServiceAppEntityRepository extends JpaRepository<ServiceAppEntity, Integer> {
  Optional<ServiceAppEntity> findByToken(UUID token);
}