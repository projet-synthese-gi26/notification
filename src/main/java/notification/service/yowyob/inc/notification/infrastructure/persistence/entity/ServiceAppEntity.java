package notification.service.yowyob.inc.notification.infrastructure.persistence.entity;

import java.util.UUID;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("service_app")
public class ServiceAppEntity {

  @Id
  private int serviceId;
  private String name;
  private UUID token;

}
