package notification.service.yowyob.inc.notification.application.domain.service;

import lombok.AllArgsConstructor;
import notification.service.yowyob.inc.notification.application.domain.model.PullTemplate;
import notification.service.yowyob.inc.notification.application.domain.repository.PullTemplateRepository;
import notification.service.yowyob.inc.notification.application.port.input.dto.TemplateCreateRequest;

@AllArgsConstructor
public class PullTemplateService {
  private PullTemplateRepository pullTemplateRepository;

  public PullTemplate createPullTemplate(TemplateCreateRequest request) {
    PullTemplate template = new PullTemplate();

    template.setName(request.getName());
    template.setDescription(request.getDescription());
    ((PullTemplate) template).setMessage(request.getMessage());

    return this.pullTemplateRepository.save((PullTemplate) template);
  }

}
