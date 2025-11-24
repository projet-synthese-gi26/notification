package notification.service.yowyob.inc.notification.application.domain.service;

import lombok.AllArgsConstructor;
import notification.service.yowyob.inc.notification.application.domain.enums.NotificationType;
import notification.service.yowyob.inc.notification.application.domain.model.ServiceApp;
import notification.service.yowyob.inc.notification.application.domain.model.Template;
import notification.service.yowyob.inc.notification.application.port.input.dto.TemplateCreateRequest;

@AllArgsConstructor
public class TemplateFactory {
  private final SMSTemplateService templateService;
  private final EmailTemplateService emailTemplateService;
  private final PullTemplateService pullTemplateService;
  private final ServiceAppService serviceAppService;

  public Template createTemplate(String token, TemplateCreateRequest request) {
    ServiceApp serviceApp = this.serviceAppService.getServiceAppByToken(token);

    Template template = null;

    if (request.getType() == NotificationType.EMAIL) {
      template = this.emailTemplateService.createEmailTemplate(request, serviceApp);
    } else if (request.getType() == NotificationType.SMS) {
      template = this.templateService.createSMSTemplate(request, serviceApp);
    } else if (request.getType() == NotificationType.PULL) {
      template = this.pullTemplateService.createPullTemplate(request, serviceApp);
    }

    return template;
  }

}
