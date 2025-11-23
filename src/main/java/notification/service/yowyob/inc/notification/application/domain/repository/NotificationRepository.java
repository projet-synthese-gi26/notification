package notification.service.yowyob.inc.notification.application.domain.repository;

import notification.service.yowyob.inc.notification.application.domain.model.Notification;

public interface NotificationRepository {
  public Notification save(Notification notification);
}
