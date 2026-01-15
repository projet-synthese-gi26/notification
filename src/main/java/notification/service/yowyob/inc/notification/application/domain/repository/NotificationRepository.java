package notification.service.yowyob.inc.notification.application.domain.repository;

import notification.service.yowyob.inc.notification.application.domain.model.Notification;
import reactor.core.publisher.Mono;

public interface NotificationRepository {
  Mono<Notification> save(Notification notification);
}
