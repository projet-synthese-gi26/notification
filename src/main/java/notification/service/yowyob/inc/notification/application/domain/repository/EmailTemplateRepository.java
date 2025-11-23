package notification.service.yowyob.inc.notification.application.domain.repository;

import notification.service.yowyob.inc.notification.application.domain.model.EmailTemplate;

public interface EmailTemplateRepository {
  public EmailTemplate save(EmailTemplate emailTemplate);

  public EmailTemplate findById(int id);
}
