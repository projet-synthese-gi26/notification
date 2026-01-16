package notification.service.yowyob.inc.notification.application.domain.service.sender;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import notification.service.yowyob.inc.notification.application.domain.model.ServiceApp;
import notification.service.yowyob.inc.notification.application.domain.repository.SMSSenderRepository;
import notification.service.yowyob.inc.notification.application.domain.repository.SMSTemplateRepository;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class SMSSenderService implements SenderStrategy {

  private final SMSTemplateRepository smsTemplateRepository;
  private final SMSSenderRepository smsSenderRepository;

  @Override
  public Mono<Void> execute(ServiceApp serviceApp, int templateId, List<String> to, Map<String, String> data) {
    return Mono.empty(); // No SMS sending implemented
  }
}