package notification.service.yowyob.inc.notification.application.domain.model;

import lombok.Data;

@Data
public class EmailSender {
  private int id;
  private String serverHost;
  private String serverPort;
  private String username;
  private String password;
}
