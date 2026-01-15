package notification.service.yowyob.inc.notification.application.domain.repository;

import notification.service.yowyob.inc.notification.application.domain.model.PullTemplate;
import notification.service.yowyob.inc.notification.application.domain.model.ServiceApp;
import reactor.core.publisher.Mono;

public interface PullTemplateRepository {
  Mono<PullTemplate> save(PullTemplate pullTemplate);
  Mono<PullTemplate> findById(int id);
  Mono<PullTemplate> findByServiceApp(ServiceApp serviceApp);
}
