package notification.service.yowyob.inc.notification.application.domain.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import lombok.AllArgsConstructor;
import notification.service.yowyob.inc.notification.application.domain.enums.NotificationStatus;
import notification.service.yowyob.inc.notification.application.domain.enums.NotificationType;
import notification.service.yowyob.inc.notification.application.domain.model.Notification;
import notification.service.yowyob.inc.notification.application.domain.repository.NotificationRepository;
import notification.service.yowyob.inc.notification.application.domain.service.sender.ContextSenderStrategy;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class NotificationService {
  private final ContextSenderStrategy contextSenderStrategy;
  private final ServiceAppService serviceAppService;
  private final NotificationRepository notificationRepository;

  public Mono<Void> send(String token, NotificationType notificationType, int templateId, List<String> to,
      Map<String, String> data) {
    return serviceAppService.getServiceAppByToken(token)
        .flatMap(serviceApp -> contextSenderStrategy.getSenderStrategy(notificationType)
            .execute(serviceApp, templateId, to, data));
  }

  public Mono<Notification> create(String token, NotificationType notificationType, int templateId, UUID userId,
      Map<String, String> data) {
    return serviceAppService.getServiceAppByToken(token)
        .flatMap(serviceApp -> {
            Notification notification = new Notification();
            notification.setUserId(userId);
            notification.setStatus(NotificationStatus.PENDING);
            notification.setData(data);
            notification.setNotificationType(notificationType);
            notification.setTemplateId(templateId);
            notification.setServiceAppId(serviceApp.getServiceId()); // Changed to serviceAppId
            notification.setCreatedAt(LocalDateTime.now());
            return notificationRepository.save(notification);
        });
  }
}
