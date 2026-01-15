package notification.service.yowyob.inc.notification.infrastructure.persistence.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("push_template")
public class PushTemplateEntity {

  @Id
  private Integer templateId;
  private String name;
  private String content;
  private String type; // Should be PUSH
  private String title;
  private String body;
  private String imageUrl;
  private String clickAction;

  private Integer serviceAppId;
}
