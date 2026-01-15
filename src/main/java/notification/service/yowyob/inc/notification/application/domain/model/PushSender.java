package notification.service.yowyob.inc.notification.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PushSender {
  private Integer pushSenderId;
  private Integer serviceAppId;
  private String serviceAccountJson;
}
