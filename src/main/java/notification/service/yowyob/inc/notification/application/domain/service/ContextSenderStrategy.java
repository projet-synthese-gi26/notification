package notification.service.yowyob.inc.notification.application.domain.service;

import java.util.Map;

import lombok.AllArgsConstructor;
import notification.service.yowyob.inc.notification.application.domain.enums.NotificationType;

@AllArgsConstructor
public class ContextSenderStrategy {
  private final Map<NotificationType, SenderStrategy> senderStrategyMap;

  public SenderStrategy getSenderStrategy(NotificationType notificationType) {
    return senderStrategyMap.get(notificationType);
  }

}
