package notification.service.yowyob.inc.notification.application.domain.service.template;

import lombok.AllArgsConstructor;
import notification.service.yowyob.inc.notification.application.domain.model.SMSTemplate;
import notification.service.yowyob.inc.notification.application.domain.model.ServiceApp;
import notification.service.yowyob.inc.notification.application.domain.model.Template;
import notification.service.yowyob.inc.notification.application.domain.repository.SMSTemplateRepository;
import notification.service.yowyob.inc.notification.application.port.input.dto.TemplateCreateRequest;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class SMSTemplateService {
  private final SMSTemplateRepository smsTemplateRepository;

  public Mono<Template> createSMSTemplate(TemplateCreateRequest request, ServiceApp serviceApp) {
    SMSTemplate template = new SMSTemplate();

    template.setName(request.getName());
    template.setDescription(request.getDescription());
    template.setMessage(request.getMessage());
    template.setServiceAppId(serviceApp.getServiceId());

    return smsTemplateRepository.save(template)
        .cast(Template.class);
  }
}
