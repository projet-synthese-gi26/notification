package notification.service.yowyob.inc.notification.application.domain.service.sender;

import java.util.List;
import java.util.Map;

import notification.service.yowyob.inc.notification.application.domain.model.ServiceApp;
import reactor.core.publisher.Mono;

public interface SenderStrategy {
  Mono<Void> execute(ServiceApp serviceApp, int templateId, List<String> to, Map<String, String> data);
}
