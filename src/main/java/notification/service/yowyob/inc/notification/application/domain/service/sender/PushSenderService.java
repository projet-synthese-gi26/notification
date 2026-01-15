package notification.service.yowyob.inc.notification.application.domain.service.sender;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import notification.service.yowyob.inc.notification.application.domain.model.PushTemplate;
import notification.service.yowyob.inc.notification.application.domain.model.ServiceApp;
import notification.service.yowyob.inc.notification.application.domain.repository.PushSenderRepository;
import notification.service.yowyob.inc.notification.application.domain.repository.PushTemplateRepository;
import notification.service.yowyob.inc.notification.application.port.output.service.PushSenderServiceInterface;
import reactor.core.publisher.Mono;
import notification.service.yowyob.inc.notification.application.domain.model.PushSender;

@AllArgsConstructor
public class PushSenderService implements SenderStrategy {

  PushTemplateRepository pushTemplateRepository;
  PushSenderRepository pushSenderRepository;
  PushSenderServiceInterface pushSenderServiceInterface;

  @Override
  public Mono<Void> execute(ServiceApp serviceApp, int templateId, List<String> to, Map<String, String> data) {
    return pushTemplateRepository.findById(templateId)
        .zipWith(pushSenderRepository.findByServiceApp(serviceApp))
        .flatMap(tuple -> {
            PushTemplate template = tuple.getT1();
            PushSender pushSender = tuple.getT2();

            // The 'to' list in execute represents device tokens for push notifications
            return this.pushSenderServiceInterface.sendPush(pushSender, template, to, data);
        });
  }
}
