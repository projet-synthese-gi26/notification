package notification.service.yowyob.inc.notification.application.port.input.dto;

import lombok.Data;
import notification.service.yowyob.inc.notification.application.domain.enums.NotificationType;

import java.util.List;
import java.util.Map;

@Data
public class NotificationSendRequest {
  private NotificationType notificationType;
  private int templateId;
  private List<String> to;
  private Map<String, String> data;
}