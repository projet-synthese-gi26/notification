package notification.service.yowyob.inc.notification.application.domain.repository;

import notification.service.yowyob.inc.notification.application.domain.model.SMSTemplate;
import notification.service.yowyob.inc.notification.application.domain.model.ServiceApp;
import reactor.core.publisher.Mono;

public interface SMSTemplateRepository {
  Mono<SMSTemplate> save(SMSTemplate smsTemplate);
  Mono<SMSTemplate> findById(int id);
  Mono<SMSTemplate> findByServiceApp(ServiceApp serviceApp);
}
