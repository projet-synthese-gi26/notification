package notification.service.yowyob.inc.notification.application.port.output.service;

import reactor.core.publisher.Mono;

import java.util.List;

public interface WhatsappSenderServiceInterface {
  Mono<Void> sendWhatsappMessage(
      String apiUrl,
      String apiTokenInstance,
      String idInstance,
      String body,
      List<String> to
      );
}
