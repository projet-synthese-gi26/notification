package notification.service.yowyob.inc.notification.application.domain.service.template;

import lombok.AllArgsConstructor;
import notification.service.yowyob.inc.notification.application.domain.model.ServiceApp;
import notification.service.yowyob.inc.notification.application.domain.model.WhatsappTemplate;
import notification.service.yowyob.inc.notification.application.domain.repository.WhatsappTemplateRepository;
import notification.service.yowyob.inc.notification.application.port.input.dto.TemplateCreateRequest;

@AllArgsConstructor
public class WhatsappTemplateService {
  private WhatsappTemplateRepository whatsappTemplateRepository;

  public WhatsappTemplate createWhatsappTemplate(TemplateCreateRequest request, ServiceApp serviceApp) {
    WhatsappTemplate template = new WhatsappTemplate();

    template.setName(request.getName());
    template.setDescription(request.getDescription());
    template.setBody(request.getBody());
    template.setServiceAppId(serviceApp.getServiceId());

    return this.whatsappTemplateRepository.save(template);
  }

}
