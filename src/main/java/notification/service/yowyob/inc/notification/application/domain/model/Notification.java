package notification.service.yowyob.inc.notification.application.domain.model;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import lombok.Data;
import notification.service.yowyob.inc.notification.application.domain.enums.NotificationStatus;
import notification.service.yowyob.inc.notification.application.domain.enums.NotificationType;

@Data
public class Notification {
  private int notificationId;
  private UUID userId;
  private int templateId;
  private ServiceApp serviceApp;
  private NotificationType notificationType;
  private NotificationStatus status;
  private LocalDateTime createdAt;
  private Map<String, String> data;
}
