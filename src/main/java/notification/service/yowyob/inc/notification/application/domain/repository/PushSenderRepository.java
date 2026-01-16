package notification.service.yowyob.inc.notification.application.domain.repository;

import notification.service.yowyob.inc.notification.application.domain.model.PushSender;
import notification.service.yowyob.inc.notification.application.domain.model.ServiceApp;
import reactor.core.publisher.Mono;

public interface PushSenderRepository {
  Mono<PushSender> save(PushSender pushSender);
  Mono<PushSender> findByServiceApp(ServiceApp serviceApp);
  Mono<PushSender> findByServiceAppId(Integer serviceAppId);
}
