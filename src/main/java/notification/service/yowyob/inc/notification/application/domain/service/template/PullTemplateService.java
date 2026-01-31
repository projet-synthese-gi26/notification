package notification.service.yowyob.inc.notification.application.domain.service.template;

import lombok.AllArgsConstructor;
import notification.service.yowyob.inc.notification.application.domain.model.PullTemplate;
import notification.service.yowyob.inc.notification.application.domain.model.ServiceApp;
import notification.service.yowyob.inc.notification.application.domain.model.Template;
import notification.service.yowyob.inc.notification.application.domain.repository.PullTemplateRepository;
import notification.service.yowyob.inc.notification.application.port.input.dto.TemplateCreateRequest;
import reactor.core.publisher.Flux;
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

  public Flux<PullTemplate> findAllByServiceAppId(Integer serviceAppId) {
    return pullTemplateRepository.findAllByServiceAppId(serviceAppId);
  }

  public Mono<PullTemplate> findById(Integer id) {
    return pullTemplateRepository.findById(id);
  }

  public Mono<Template> updatePullTemplate(Integer id, TemplateCreateRequest request) {
    return pullTemplateRepository.findById(id)
      .flatMap(template -> {
          template.setName(request.getName());
          template.setDescription(request.getDescription());
          template.setMessage(request.getMessage());
          return pullTemplateRepository.save(template);
      }).cast(Template.class);
  }

  public Mono<Void> deleteById(Integer id) {
    return pullTemplateRepository.deleteById(id);
  }
}
