package notification.service.yowyob.inc.notification.application.domain.service.sender;

import notification.service.yowyob.inc.notification.application.domain.enums.NotificationType;
import notification.service.yowyob.inc.notification.application.exception.UnsupportedNotificationTypeException;

import java.util.Map;
import java.util.Optional;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ContextSenderStrategy {
  private final Map<NotificationType, SenderStrategy> senderStrategyMap;

    public SenderStrategy getSenderStrategy(NotificationType notificationType) {
        return Optional.ofNullable(senderStrategyMap.get(notificationType))
                .orElseThrow(() -> new UnsupportedNotificationTypeException(
                        "Unsupported notification type: " + notificationType
                ));
    }

}
