package notification.service.yowyob.inc.notification.application.domain.repository;

import notification.service.yowyob.inc.notification.application.domain.model.ServiceApp;
import notification.service.yowyob.inc.notification.application.domain.model.WhatsappSender;

public interface WhatsappSenderRepository {
  public WhatsappSender save(WhatsappSender whatsappSender);

  public WhatsappSender findByServiceApp(ServiceApp serviceApp);

  // public WhatsappSender update(WhatsappSender whatsappSender);
}
