package notification.service.yowyob.inc.notification.infrastructure.persistence.entity;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import notification.service.yowyob.inc.notification.application.domain.enums.NotificationStatus;
import notification.service.yowyob.inc.notification.application.domain.enums.NotificationType;

@Data
@Entity
public class NotificationEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int notificationId;
  private UUID userId;
  private int templateId;

  @Enumerated(EnumType.STRING)
  private NotificationType notificationType;

  @Enumerated(EnumType.STRING)
  private NotificationStatus status;

  private LocalDateTime createdAt;

  private Map<String, String> data;

  @ManyToOne
  private ServiceAppEntity serviceApp;

}
