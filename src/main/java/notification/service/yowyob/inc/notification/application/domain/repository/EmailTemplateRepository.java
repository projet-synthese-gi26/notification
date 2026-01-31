package notification.service.yowyob.inc.notification.application.domain.repository;

import notification.service.yowyob.inc.notification.application.domain.model.EmailTemplate;
import notification.service.yowyob.inc.notification.application.domain.model.ServiceApp;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmailTemplateRepository {
  Mono<EmailTemplate> save(EmailTemplate emailTemplate);

  Mono<EmailTemplate> findById(int id);

  Mono<EmailTemplate> findByServiceApp(ServiceApp serviceApp);

  Flux<EmailTemplate> findAllByServiceAppId(Integer serviceAppId);

  Mono<Void> deleteById(Integer id);

  Mono<EmailTemplate> findById(Integer id);
}
