package notification.service.yowyob.inc.notification.application.domain.repository;

import notification.service.yowyob.inc.notification.application.domain.model.EmailSender;
import notification.service.yowyob.inc.notification.application.domain.model.ServiceApp;
import reactor.core.publisher.Mono;

public interface EmailSenderRepository {
  Mono<EmailSender> save(EmailSender emailSender);
  Mono<EmailSender> findByServiceApp(ServiceApp serviceApp);
}
