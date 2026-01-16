package notification.service.yowyob.inc.notification.application.domain.repository;

import notification.service.yowyob.inc.notification.application.domain.model.ServiceApp;
import notification.service.yowyob.inc.notification.application.domain.model.WhatsappSender;
import reactor.core.publisher.Mono;

public interface WhatsappSenderRepository {
  Mono<WhatsappSender> save(WhatsappSender whatsappSender);

  Mono<WhatsappSender> findByServiceApp(ServiceApp serviceApp);

  Mono<WhatsappSender> findByServiceAppId(Integer serviceAppId);
}
