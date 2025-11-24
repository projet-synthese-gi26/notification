package notification.service.yowyob.inc.notification.application.domain.service;

import lombok.AllArgsConstructor;
import notification.service.yowyob.inc.notification.application.domain.enums.NotificationType;
import notification.service.yowyob.inc.notification.application.domain.model.Template;
import notification.service.yowyob.inc.notification.application.port.input.dto.TemplateCreateRequest;

@AllArgsConstructor
public class TemplateFactory {
  SMSTemplateService templateService;
  EmailTemplateService emailTemplateService;
  PullTemplateService pullTemplateService;

  public Template createTemplate(TemplateCreateRequest request) {
    Template template = null;

    if (request.getType() == NotificationType.EMAIL) {
      template = this.emailTemplateService.createEmailTemplate(request);
    } else if (request.getType() == NotificationType.SMS) {
      template = this.templateService.createSMSTemplate(request);
    } else if (request.getType() == NotificationType.PULL) {
      template = this.pullTemplateService.createPullTemplate(request);
    }

    return template;
  }

}
