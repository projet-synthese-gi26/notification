package notification.service.yowyob.inc.notification.infrastructure.persistence.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("sms_sender")
public class SMSSenderEntity {
  @Id
  private int smsSenderId;
  private String serverHost;
  private String serverPort;
  private String token;
  private Integer serviceAppId;
}
