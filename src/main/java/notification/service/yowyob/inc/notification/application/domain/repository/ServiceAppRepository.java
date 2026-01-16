package notification.service.yowyob.inc.notification.application.domain.repository;

import java.util.UUID;

import notification.service.yowyob.inc.notification.application.domain.model.ServiceApp;
import reactor.core.publisher.Mono;

public interface ServiceAppRepository {
  Mono<ServiceApp> save(ServiceApp serviceApp);

  Mono<ServiceApp> findByToken(UUID token);
  Mono<ServiceApp> findById(Integer id);
}
