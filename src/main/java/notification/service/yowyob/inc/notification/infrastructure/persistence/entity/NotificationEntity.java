package notification.service.yowyob.inc.notification.infrastructure.persistence.entity;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import lombok.Data;
import notification.service.yowyob.inc.notification.application.domain.enums.NotificationStatus;
import notification.service.yowyob.inc.notification.application.domain.enums.NotificationType;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("notification")
public class NotificationEntity {
  @Id
  private int notificationId;
  private UUID userId;
  private int templateId;
  private NotificationType notificationType;
  private NotificationStatus status;
  private LocalDateTime createdAt;
  private Map<String, String> data;
  private Integer serviceAppId;
}
