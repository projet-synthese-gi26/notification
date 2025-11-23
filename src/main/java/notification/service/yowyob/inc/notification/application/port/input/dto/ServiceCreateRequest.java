package notification.service.yowyob.inc.notification.application.port.input.dto;

import lombok.Data;

@Data
public class ServiceCreateRequest {
  private String name;

  // Information d'envois de SMS et de l'email
  private String emailServerHost;
  private String emailServerPort;
  private String emailUsername;
  private String emailPassword;

  // SMS
  private String smsServerHost;
  private String smsServerPort;
  private String smstoken;
}
