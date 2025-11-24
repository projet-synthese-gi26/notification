package notification.service.yowyob.inc.notification.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class EmailSenderEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int emailSenderId;
  private String serverHost;
  private String serverPort;
  private String username;
  private String password;
}
