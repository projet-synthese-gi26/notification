package notification.service.yowyob.inc.notification.application.domain.model;

import lombok.Data;

@Data
public abstract class Template {
  private Integer templateId;
  private Integer serviceAppId;
  private String name;
  private String description;
}
