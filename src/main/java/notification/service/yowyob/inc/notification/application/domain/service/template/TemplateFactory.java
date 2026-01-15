package notification.service.yowyob.inc.notification.application.domain.service.template;

import lombok.AllArgsConstructor;
import notification.service.yowyob.inc.notification.application.domain.enums.NotificationType;
import notification.service.yowyob.inc.notification.application.domain.model.Template;
import notification.service.yowyob.inc.notification.application.domain.service.ServiceAppService;
import notification.service.yowyob.inc.notification.application.port.input.dto.TemplateCreateRequest;
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

}
