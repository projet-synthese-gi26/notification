package notification.service.yowyob.inc.notification.infrastructure.persistence.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("sms_template")
public class SMSTemplateEntity {

  private String message;
  @Id
  private int templateId;
  private String name;
  private String description;
  private Integer serviceAppId;
}
