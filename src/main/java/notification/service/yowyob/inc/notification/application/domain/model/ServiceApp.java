package notification.service.yowyob.inc.notification.application.domain.model;

import java.util.UUID;

import lombok.Data;

@Data
public class ServiceApp {
  private int serviceId;
  private String name;
  private UUID token;
}
