package notification.service.yowyob.inc.notification.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailSender {
  private Integer emailSenderId;
  private Integer serviceAppId;
  private String serverHost;
  private String serverPort;
  private String username;
  private String password;
}
