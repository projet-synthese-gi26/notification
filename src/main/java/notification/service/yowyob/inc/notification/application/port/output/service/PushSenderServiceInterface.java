package notification.service.yowyob.inc.notification.application.port.output.service;

import java.util.List;
import java.util.Map;

import notification.service.yowyob.inc.notification.application.domain.model.PushSender;
import notification.service.yowyob.inc.notification.application.domain.model.PushTemplate;
import reactor.core.publisher.Mono;

public interface PushSenderServiceInterface {
    Mono<Void> sendPush(PushSender sender, PushTemplate template, List<String> deviceTokens, Map<String, String> data);
}
