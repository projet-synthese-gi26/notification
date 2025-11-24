package notification.service.yowyob.inc.notification.infrastructure.persistence.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("pull_template")
public class PullTemplateEntity {
  @Id
  private int templateId;
  private String name;
  private String description;
  private String message;
  private Integer serviceAppId;
}
