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
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class WhatsappSenderService implements SenderStrategy {

  private final WhatsappTemplateRepository whatsappTemplateRepository;
  private final WhatsappSenderServiceInterface whatsappSenderServiceInterface;
  private final WhatsappSenderRepository whatsappSenderRepository;

  @Override
  public Mono<Void> execute(ServiceApp serviceApp, int templateId, List<String> to, Map<String, String> data) {
    return whatsappTemplateRepository.findByServiceApp(serviceApp)
        .zipWith(whatsappSenderRepository.findByServiceApp(serviceApp))
        .flatMap(tuple -> {
            WhatsappTemplate whatsappTemplate = tuple.getT1();
            WhatsappSender whatsappSender = tuple.getT2();
            String body = Utils.replaceVariables(whatsappTemplate.getBody(), data);

            return whatsappSenderServiceInterface.sendWhatsappMessage(
                whatsappSender.getApiUrl(),
                whatsappSender.getApiTokenInstance(),
                whatsappSender.getIdInstance(),
                body,
                to
            );
        });
  }

}
