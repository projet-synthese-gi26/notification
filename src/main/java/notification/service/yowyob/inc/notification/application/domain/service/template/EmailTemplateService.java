package notification.service.yowyob.inc.notification.application.domain.service.template;

import lombok.AllArgsConstructor;
import notification.service.yowyob.inc.notification.application.domain.model.EmailTemplate;
import notification.service.yowyob.inc.notification.application.domain.model.ServiceApp;
import notification.service.yowyob.inc.notification.application.domain.model.Template;
import notification.service.yowyob.inc.notification.application.domain.repository.EmailTemplateRepository;
import notification.service.yowyob.inc.notification.application.port.input.dto.TemplateCreateRequest;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class EmailTemplateService {
  private final EmailTemplateRepository emailTemplateRepository;

  public Mono<Template> createEmailTemplate(TemplateCreateRequest request, ServiceApp serviceApp) {
    EmailTemplate template = new EmailTemplate();

    template.setName(request.getName());
    template.setFromEmail(request.getFromEmail());
    template.setDescription(request.getDescription());
    template.setSubject(request.getSubject());
    template.setBodyHtml(request.getBodyHtml());
    template.setServiceAppId(serviceApp.getServiceId());

    return emailTemplateRepository.save(template)
        .cast(Template.class);
  }
}
