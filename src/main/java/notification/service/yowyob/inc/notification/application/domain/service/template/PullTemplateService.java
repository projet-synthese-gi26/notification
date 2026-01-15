package notification.service.yowyob.inc.notification.application.domain.service.template;

import lombok.AllArgsConstructor;
import notification.service.yowyob.inc.notification.application.domain.model.PullTemplate;
import notification.service.yowyob.inc.notification.application.domain.model.ServiceApp;
import notification.service.yowyob.inc.notification.application.domain.model.Template;
import notification.service.yowyob.inc.notification.application.domain.repository.PullTemplateRepository;
import notification.service.yowyob.inc.notification.application.port.input.dto.TemplateCreateRequest;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class PullTemplateService {
  private final PullTemplateRepository pullTemplateRepository;

  public Mono<Template> createPullTemplate(TemplateCreateRequest request, ServiceApp serviceApp) {
    PullTemplate template = new PullTemplate();

    template.setName(request.getName());
    template.setDescription(request.getDescription());
    template.setMessage(request.getMessage());
    template.setServiceAppId(serviceApp.getServiceId());

    return pullTemplateRepository.save(template)
        .cast(Template.class);
  }
}
