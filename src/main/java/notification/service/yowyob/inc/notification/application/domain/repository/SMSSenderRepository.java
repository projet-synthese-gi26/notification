package notification.service.yowyob.inc.notification.application.domain.repository;

import notification.service.yowyob.inc.notification.application.domain.model.SMSSender;
import notification.service.yowyob.inc.notification.application.domain.model.ServiceApp;
import reactor.core.publisher.Mono;

public interface SMSSenderRepository {
  Mono<SMSSender> save(SMSSender smsSender);
  Mono<SMSSender> findByServiceApp(ServiceApp serviceApp);
}
