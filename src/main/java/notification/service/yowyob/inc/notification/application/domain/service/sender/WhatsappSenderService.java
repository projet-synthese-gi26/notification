package notification.service.yowyob.inc.notification.application.domain.service.sender;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import notification.service.yowyob.inc.notification.application.domain.model.ServiceApp;
import notification.service.yowyob.inc.notification.application.domain.model.WhatsappSender;
import notification.service.yowyob.inc.notification.application.domain.model.WhatsappTemplate;
import notification.service.yowyob.inc.notification.application.domain.repository.WhatsappSenderRepository;
import notification.service.yowyob.inc.notification.application.domain.repository.WhatsappTemplateRepository;
import notification.service.yowyob.inc.notification.application.port.output.service.WhatsappSenderServiceInterface;

@AllArgsConstructor
public class WhatsappSenderService implements SenderStrategy {

  WhatsappTemplateRepository whatsappTemplateRepository;
  WhatsappSenderServiceInterface whatsappSenderServiceInterface;
  WhatsappSenderRepository whatsappSenderRepository;

  @Override
  public void execute(ServiceApp serviceApp, int templateId, List<String> to, Map<String, String> data) {
    WhatsappTemplate whatsappTemplate = this.whatsappTemplateRepository.findByServiceApp(serviceApp);
    String body = Utils.replaceVariables(whatsappTemplate.getBody(), data);
    WhatsappSender whatsappSender = this.whatsappSenderRepository.findByServiceApp(serviceApp);

    this.whatsappSenderServiceInterface.sendWhatsappMessage(whatsappSender.getApiUrl(),
        whatsappSender.getApiTokenInstance(), whatsappSender.getIdInstance(), body);
  }

}
