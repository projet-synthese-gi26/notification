package notification.service.yowyob.inc.notification.application.port.input.dto;

import lombok.Data;
import notification.service.yowyob.inc.notification.application.domain.enums.NotificationType;

import java.util.Map;
import java.util.UUID;

@Data
public class NotificationCreateRequest {
  private NotificationType notificationType;
  private int templateId;
  private UUID userId;
  private Map<String, String> data;
}