package notification.service.yowyob.inc.notification.application.domain.model;

import lombok.Data;

@Data
public abstract class Template {
  private int templateId;
  private int serviceAppId;
  private String name;
  private String description;
}
