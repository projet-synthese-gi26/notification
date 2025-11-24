package notification.service.yowyob.inc.notification.application.domain.service;

import lombok.AllArgsConstructor;
import notification.service.yowyob.inc.notification.application.domain.model.ServiceApp;
import notification.service.yowyob.inc.notification.application.domain.model.EmailTemplate;
import notification.service.yowyob.inc.notification.application.domain.repository.EmailTemplateRepository;
import notification.service.yowyob.inc.notification.application.port.input.dto.TemplateCreateRequest;

@AllArgsConstructor
public class EmailTemplateService {
  private EmailTemplateRepository emailTemplateRepository;

  public EmailTemplate createEmailTemplate(TemplateCreateRequest request, ServiceApp serviceApp) {
    EmailTemplate template = new EmailTemplate();

    template.setName(request.getName());
    template.setFromEmail(request.getFromEmail());
    template.setDescription(request.getDescription());
    template.setSubject(request.getSubject());
    template.setBodyHtml(request.getBodyHtml());
    template.setServiceAppId(serviceApp.getServiceId());

    return this.emailTemplateRepository.save(template);
  }
}
