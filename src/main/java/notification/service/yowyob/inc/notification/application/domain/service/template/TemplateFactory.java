package notification.service.yowyob.inc.notification.application.domain.service.template;

import lombok.AllArgsConstructor;
import notification.service.yowyob.inc.notification.application.domain.enums.NotificationType;
import notification.service.yowyob.inc.notification.application.domain.model.Template;
import notification.service.yowyob.inc.notification.application.domain.service.ServiceAppService;
import notification.service.yowyob.inc.notification.application.port.input.dto.TemplateCreateRequest;
import notification.service.yowyob.inc.notification.application.port.input.dto.TemplatePreview;

import notification.service.yowyob.inc.notification.application.exception.UnauthorizedException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class TemplateFactory {
  private final SMSTemplateService smsTemplateService;
  private final EmailTemplateService emailTemplateService;
  private final PullTemplateService pullTemplateService;
  private final ServiceAppService serviceAppService;
  private final WhatsappTemplateService whatsappTemplateService;

  public Mono<Template> createTemplate(String token, TemplateCreateRequest request) {
    return serviceAppService.getServiceAppByToken(token)
        .flatMap(serviceApp -> {
            if (request.getType() == NotificationType.EMAIL) {
                return emailTemplateService.createEmailTemplate(request, serviceApp);
            } else if (request.getType() == NotificationType.SMS) {
                return smsTemplateService.createSMSTemplate(request, serviceApp);
            } else if (request.getType() == NotificationType.PULL) {
                return pullTemplateService.createPullTemplate(request, serviceApp);
            } else if (request.getType() == NotificationType.WHATSAPP) {
                return whatsappTemplateService.createWhatsappTemplate(request, serviceApp);
            }
            return Mono.error(new IllegalArgumentException("Unsupported template type"));
        });
  }

  public Flux<TemplatePreview> findAllTemplatesByServiceAppId(String token) {
    return serviceAppService.getServiceAppByToken(token)
        .flatMapMany(serviceApp -> Flux.merge(
            emailTemplateService.findAllByServiceAppId(serviceApp.getServiceId())
                .map(t -> new TemplatePreview(String.valueOf(t.getTemplateId()), t.getName(), t.getDescription())),
            smsTemplateService.findAllByServiceAppId(serviceApp.getServiceId())
                .map(t -> new TemplatePreview(String.valueOf(t.getTemplateId()), t.getName(), t.getDescription())),
            pullTemplateService.findAllByServiceAppId(serviceApp.getServiceId())
                .map(t -> new TemplatePreview(String.valueOf(t.getTemplateId()), t.getName(), t.getDescription())),
            whatsappTemplateService.findAllByServiceAppId(serviceApp.getServiceId())
                .map(t -> new TemplatePreview(String.valueOf(t.getTemplateId()), t.getName(), t.getDescription()))
        ));
  }

  public Mono<Template> getTemplateById(String token, Integer id, NotificationType type) {
    return serviceAppService.getServiceAppByToken(token).flatMap(serviceApp -> {
      Mono<Template> templateMono;
      if (type == NotificationType.EMAIL) {
        templateMono = emailTemplateService.findById(id).cast(Template.class);
      } else if (type == NotificationType.SMS) {
        templateMono = smsTemplateService.findById(id).cast(Template.class);
      } else if (type == NotificationType.PULL) {
        templateMono = pullTemplateService.findById(id).cast(Template.class);
      } else if (type == NotificationType.WHATSAPP) {
        templateMono = whatsappTemplateService.findById(id).cast(Template.class);
      } else {
        return Mono.error(new IllegalArgumentException("Unsupported template type"));
      }

      return templateMono.flatMap(template -> {
        if (!template.getServiceAppId().equals(serviceApp.getServiceId())) {
          return Mono.error(new UnauthorizedException("Service not authorized for this template"));
        }
        return Mono.just(template);
      });
    });
  }

  public Mono<Template> updateTemplate(String token, Integer id, NotificationType type, TemplateCreateRequest request) {
    return getTemplateById(token, id, type) // getTemplateById handles authorization
        .flatMap(existingTemplate -> {
            if (type == NotificationType.EMAIL) {
                return emailTemplateService.updateEmailTemplate(id, request);
            } else if (type == NotificationType.SMS) {
                return smsTemplateService.updateSMSTemplate(id, request);
            } else if (type == NotificationType.PULL) {
                return pullTemplateService.updatePullTemplate(id, request);
            } else if (type == NotificationType.WHATSAPP) {
                return whatsappTemplateService.updateWhatsappTemplate(id, request);
            }
            return Mono.error(new IllegalArgumentException("Unsupported template type"));
        });
  }

  public Mono<Void> deleteTemplate(String token, Integer id, NotificationType type) {
    return getTemplateById(token, id, type) // getTemplateById handles authorization
        .flatMap(existingTemplate -> {
            if (type == NotificationType.EMAIL) {
                return emailTemplateService.deleteById(id);
            } else if (type == NotificationType.SMS) {
                return smsTemplateService.deleteById(id);
            } else if (type == NotificationType.PULL) {
                return pullTemplateService.deleteById(id);
            } else if (type == NotificationType.WHATSAPP) {
                return whatsappTemplateService.deleteById(id);
            }
            return Mono.error(new IllegalArgumentException("Unsupported template type"));
        }).then();
  }
}
