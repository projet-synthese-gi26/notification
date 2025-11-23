package notification.service.yowyob.inc.notification.application.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PullTemplate extends Template {
  private String message;
}
