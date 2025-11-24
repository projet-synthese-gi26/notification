package notification.service.yowyob.inc.notification.application.domain.service;

import lombok.AllArgsConstructor;
import notification.service.yowyob.inc.notification.application.domain.model.ServiceApp;
import notification.service.yowyob.inc.notification.application.domain.model.PullTemplate;
import notification.service.yowyob.inc.notification.application.domain.repository.PullTemplateRepository;
import notification.service.yowyob.inc.notification.application.port.input.dto.TemplateCreateRequest;

@AllArgsConstructor
public class PullTemplateService {
  private PullTemplateRepository pullTemplateRepository;

  public PullTemplate createPullTemplate(TemplateCreateRequest request, ServiceApp serviceApp) {
    PullTemplate template = new PullTemplate();

    template.setName(request.getName());
    template.setDescription(request.getDescription());
    template.setMessage(request.getMessage());
    template.setServiceAppId(serviceApp.getServiceId());

    return this.pullTemplateRepository.save(template);
  }

}
