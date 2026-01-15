package notification.service.yowyob.inc.notification.application.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class WhatsappTemplate extends Template {
  private String body;
}
