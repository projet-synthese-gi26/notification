package notification.service.yowyob.inc.notification.application.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class EmailTemplate extends Template {
  private int id;
  private String name;
  private String description;
  private String fromEmail;
  private String subject;
  private String bodyHtml;
}
