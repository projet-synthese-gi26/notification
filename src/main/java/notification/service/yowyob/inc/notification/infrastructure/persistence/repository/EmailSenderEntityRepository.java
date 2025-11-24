package notification.service.yowyob.inc.notification.infrastructure.persistence.repository;

import notification.service.yowyob.inc.notification.infrastructure.persistence.entity.EmailSenderEntity;
import notification.service.yowyob.inc.notification.infrastructure.persistence.entity.ServiceAppEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailSenderEntityRepository extends JpaRepository<EmailSenderEntity, Integer> {
  Optional<EmailSenderEntity> findByServiceApp(ServiceAppEntity serviceApp);
}