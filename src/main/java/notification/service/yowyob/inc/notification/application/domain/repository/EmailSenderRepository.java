package notification.service.yowyob.inc.notification.application.domain.repository;

import notification.service.yowyob.inc.notification.application.domain.model.EmailSender;
import notification.service.yowyob.inc.notification.application.domain.model.ServiceApp;

public interface EmailSenderRepository {
  public EmailSender save(EmailSender emailSender);

  public EmailSender findByServiceApp(ServiceApp serviceApp);

}
