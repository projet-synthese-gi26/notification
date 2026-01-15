package notification.service.yowyob.inc.notification.application.domain.repository;

import notification.service.yowyob.inc.notification.application.domain.model.ServiceApp;
import notification.service.yowyob.inc.notification.application.domain.model.WhatsappTemplate;

public interface WhatsappTemplateRepository {
  public WhatsappTemplate save(WhatsappTemplate whatsappTemplate);

  public WhatsappTemplate findByServiceApp(ServiceApp serviceApp);
}
