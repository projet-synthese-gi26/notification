package notification.service.yowyob.inc.notification.infrastructure.persistence.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("push_sender")
public class PushSenderEntity {
  @Id
  private Integer pushSenderId;
  private String serviceAccountJson;

  private Integer serviceAppId;
}
