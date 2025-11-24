package notification.service.yowyob.inc.notification.infrastructure.persistence.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("email_sender")
public class EmailSenderEntity {
  @Id
  private int emailSenderId;
  private String serverHost;
  private String serverPort;
  private String username;
  private String password;

  private Integer serviceAppId;
}
