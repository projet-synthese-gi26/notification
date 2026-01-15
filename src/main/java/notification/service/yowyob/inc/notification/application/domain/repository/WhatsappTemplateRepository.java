package notification.service.yowyob.inc.notification.application.domain.repository;

import notification.service.yowyob.inc.notification.application.domain.model.ServiceApp;
import notification.service.yowyob.inc.notification.application.domain.model.WhatsappTemplate;
import reactor.core.publisher.Mono;

public interface WhatsappTemplateRepository {
  Mono<WhatsappTemplate> save(WhatsappTemplate whatsappTemplate);

  Mono<WhatsappTemplate> findByServiceApp(ServiceApp serviceApp);
}
