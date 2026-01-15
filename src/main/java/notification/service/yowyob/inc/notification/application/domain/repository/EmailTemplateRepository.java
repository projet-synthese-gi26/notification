package notification.service.yowyob.inc.notification.application.domain.repository;

import notification.service.yowyob.inc.notification.application.domain.model.EmailTemplate;
import notification.service.yowyob.inc.notification.application.domain.model.ServiceApp;
import reactor.core.publisher.Mono;

public interface EmailTemplateRepository {
  Mono<EmailTemplate> save(EmailTemplate emailTemplate);
  Mono<EmailTemplate> findById(int id);
  Mono<EmailTemplate> findByServiceApp(ServiceApp serviceApp);
}
