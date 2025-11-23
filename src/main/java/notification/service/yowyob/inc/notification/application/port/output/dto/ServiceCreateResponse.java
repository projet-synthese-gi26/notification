package notification.service.yowyob.inc.notification.application.port.output.dto;

import lombok.Data;

@Data
public class ServiceCreateResponse {
  private int serviceId;
  private String name;
  private String token;
}
