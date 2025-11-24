package notification.service.yowyob.inc.notification.application.domain.model;

import lombok.Data;

@Data
public class SMSSender {
  private int smsSenderId;
  private int serviceAppId;
  private String serverHost;
  private String serverPort;
  private String token;
}
