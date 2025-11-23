package notification.service.yowyob.inc.notification.application.domain.service;

import lombok.AllArgsConstructor;
import notification.service.yowyob.inc.notification.application.domain.model.SMSTemplate;
import notification.service.yowyob.inc.notification.application.domain.repository.SMSTemplateRepository;
import notification.service.yowyob.inc.notification.application.port.input.dto.TemplateCreateRequest;

@AllArgsConstructor
public class SMSTemplateService {
  private SMSTemplateRepository smsTemplateRepository;

  public SMSTemplate createSMSTemplate(TemplateCreateRequest request) {
    SMSTemplate template = new SMSTemplate();

    template.setName(request.getName());
    template.setDescription(request.getDescription());
    ((SMSTemplate) template).setMessage(request.getMessage());

    return this.smsTemplateRepository.save((SMSTemplate) template);
  }
}
