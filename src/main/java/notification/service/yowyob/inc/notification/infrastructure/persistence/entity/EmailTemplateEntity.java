package notification.service.yowyob.inc.notification.infrastructure.persistence.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("email_template")
public class EmailTemplateEntity {

  @Id
  private int templateId;
  private String name;
  private String description;
  private String fromEmail;
  private String subject;
  private String bodyHtml;

  private Integer serviceAppId;
}
