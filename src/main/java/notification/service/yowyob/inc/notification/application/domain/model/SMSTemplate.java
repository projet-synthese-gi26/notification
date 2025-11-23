package notification.service.yowyob.inc.notification.application.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SMSTemplate extends Template {
  private String message;

}
