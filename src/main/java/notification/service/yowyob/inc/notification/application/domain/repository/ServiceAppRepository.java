package notification.service.yowyob.inc.notification.application.domain.repository;

import java.util.UUID;

import notification.service.yowyob.inc.notification.application.domain.model.ServiceApp;

public interface ServiceAppRepository {
  public ServiceApp save(ServiceApp serviceApp);

  public ServiceApp findByToken(UUID token);
}
