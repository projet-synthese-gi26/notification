package notification.service.yowyob.inc.notification.application.port.input.dto;

import lombok.Data;
import notification.service.yowyob.inc.notification.application.domain.enums.NotificationType;

@Data
public class TemplateCreateRequest {
  private int templateId;
  private String name;
  private String description;
  private String message;
  private String subject;
  private String bodyHtml;
  NotificationType type;
}
