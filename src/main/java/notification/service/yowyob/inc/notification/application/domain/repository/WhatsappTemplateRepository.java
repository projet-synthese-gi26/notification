package notification.service.yowyob.inc.notification.application.domain.repository;

import notification.service.yowyob.inc.notification.application.domain.model.ServiceApp;
import notification.service.yowyob.inc.notification.application.domain.model.WhatsappTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface WhatsappTemplateRepository {
  Mono<WhatsappTemplate> save(WhatsappTemplate whatsappTemplate);

  Mono<WhatsappTemplate> findById(int id);

  Mono<WhatsappTemplate> findByServiceApp(ServiceApp serviceApp);

  Flux<WhatsappTemplate> findAllByServiceAppId(Integer serviceAppId);

  Mono<Void> deleteById(Integer id);

  Mono<WhatsappTemplate> findById(Integer id);
}
