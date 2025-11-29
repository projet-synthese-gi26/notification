package notification.service.yowyob.inc.notification.application.domain.service;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import notification.service.yowyob.inc.notification.application.domain.model.EmailSender;
import notification.service.yowyob.inc.notification.application.domain.model.EmailTemplate;
import notification.service.yowyob.inc.notification.application.domain.model.ServiceApp;
import notification.service.yowyob.inc.notification.application.domain.repository.EmailSenderRepository;
import notification.service.yowyob.inc.notification.application.domain.repository.EmailTemplateRepository;
import notification.service.yowyob.inc.notification.application.port.output.service.EmailSenderServiceInterface;

@AllArgsConstructor
public class EmailSenderService implements SenderStrategy {

  EmailTemplateRepository emailTemplateRepository;
  EmailSenderRepository emailSenderRepository;
  EmailSenderServiceInterface emailSenderServiceInterface;

  @Override
  public void execute(ServiceApp serviceApp, int templateId, List<String> to, Map<String, String> data) {
    EmailTemplate template = this.emailTemplateRepository.findById(templateId);
    EmailSender emailSender = this.emailSenderRepository.findByServiceApp(serviceApp);

    this.emailSenderServiceInterface.sendEamil(to, template.getFromEmail(), template.getBodyHtml(), data,
        template.getSubject(), emailSender.getServerHost(), emailSender.getServerPort(), emailSender.getUsername(),
        emailSender.getPassword());
  }

}
