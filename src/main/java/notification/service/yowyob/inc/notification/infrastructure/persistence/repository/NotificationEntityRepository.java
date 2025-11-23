package notification.service.yowyob.inc.notification.infrastructure.persistence.repository;

import notification.service.yowyob.inc.notification.infrastructure.persistence.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationEntityRepository extends JpaRepository<NotificationEntity, Integer> {
}