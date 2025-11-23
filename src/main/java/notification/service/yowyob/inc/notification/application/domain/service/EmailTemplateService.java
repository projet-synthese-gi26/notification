package notification.service.yowyob.inc.notification.application.domain.service;

import lombok.AllArgsConstructor;
import notification.service.yowyob.inc.notification.application.domain.model.EmailTemplate;
import notification.service.yowyob.inc.notification.application.domain.repository.EmailTemplateRepository;
import notification.service.yowyob.inc.notification.application.port.input.dto.TemplateCreateRequest;

@AllArgsConstructor
public class EmailTemplateService {
  private EmailTemplateRepository emailTemplateRepository;

  public EmailTemplate createEmailTemplate(TemplateCreateRequest request) {
    EmailTemplate template = new EmailTemplate();

    template.setName(request.getName());
    template.setDescription(request.getDescription());
    ((EmailTemplate) template).setSubject(request.getSubject());
    ((EmailTemplate) template).setBodyHtml(request.getBodyHtml());

    return this.emailTemplateRepository.save((EmailTemplate) template);
  }
}
