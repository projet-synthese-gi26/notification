package notification.service.yowyob.inc.notification.application.domain.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import lombok.AllArgsConstructor;
import notification.service.yowyob.inc.notification.application.domain.enums.NotificationStatus;
import notification.service.yowyob.inc.notification.application.domain.enums.NotificationType;
import notification.service.yowyob.inc.notification.application.domain.model.Notification;
import notification.service.yowyob.inc.notification.application.domain.model.ServiceApp;
import notification.service.yowyob.inc.notification.application.domain.repository.NotificationRepository;

@AllArgsConstructor
public class NotificationService {
  private final ContextSenderStrategy contextSenderStrategy;

  private final ServiceAppService serviceAppService;

  private final NotificationRepository notificationRepository;

  public void send(String token, NotificationType notificationType, int templateId, List<String> to,
      Map<String, String> data) {
    ServiceApp serviceApp = this.serviceAppService.getServiceAppByToken(token);
    contextSenderStrategy.getSenderStrategy(notificationType).execute(serviceApp, templateId, to, data);
  }

  public void create(String token, NotificationType notificationType, int templateId, UUID userId,
      Map<String, String> data) {
    ServiceApp serviceApp = this.serviceAppService.getServiceAppByToken(token);

    Notification notification = new Notification();
    notification.setUserId(userId);
    notification.setStatus(NotificationStatus.PENDING);
    notification.setData(data);
    notification.setNotificationType(notificationType);
    notification.setTemplateId(templateId);
    notification.setServiceApp(serviceApp);
    notification.setCreatedAt(LocalDateTime.now());

    this.notificationRepository.save(notification);
  }

}
