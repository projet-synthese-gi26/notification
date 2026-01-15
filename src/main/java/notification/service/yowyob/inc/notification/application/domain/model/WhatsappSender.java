package notification.service.yowyob.inc.notification.application.domain.model;

import lombok.Data;

@Data
public class WhatsappSender {
  private int whatsappSenderId;
  private int serviceAppId;
  private String apiUrl;
  private String idInstance;
  private String apiTokenInstance;
}
