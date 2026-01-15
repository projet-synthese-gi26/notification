package notification.service.yowyob.inc.notification.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WhatsappSender {
  private Integer whatsappSenderId;
  private Integer serviceAppId;
  private String apiUrl;
  private String idInstance;
  private String apiTokenInstance;
}
