package notification.service.yowyob.inc.notification.config;

import notification.service.yowyob.inc.notification.application.domain.enums.NotificationType;
import notification.service.yowyob.inc.notification.application.domain.repository.*;
import notification.service.yowyob.inc.notification.application.domain.service.*;
import notification.service.yowyob.inc.notification.application.port.output.service.EmailSenderServiceInterface;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class DomainConfig {

  @Bean
  public ServiceAppService serviceAppService(
      ServiceAppRepository serviceAppRepository,
      EmailSenderRepository emailSenderRepository,
      SMSSenderRepository smsSenderRepository) {
    return new ServiceAppService(serviceAppRepository, emailSenderRepository, smsSenderRepository);
  }

  @Bean
  public EmailSenderService emailSenderService(
      EmailTemplateRepository emailTemplateRepository,
      EmailSenderRepository emailSenderRepository,
      EmailSenderServiceInterface emailSenderServiceInterface) {
    return new EmailSenderService(emailTemplateRepository, emailSenderRepository, emailSenderServiceInterface);
  }

  @Bean
  public SMSSenderService smsSenderService() {
    // Ajoutez les dépendances si nécessaire
    return new SMSSenderService();
  }

  @Bean
  public ContextSenderStrategy contextSenderStrategy(Map<String, SenderStrategy> senderStrategies) {
    // Spring injecte une Map où la clé est le nom du bean (String).
    // Nous devons la transformer en Map<NotificationType, SenderStrategy>.
    Map<NotificationType, SenderStrategy> strategyMap = Map.of(
        NotificationType.EMAIL, senderStrategies.get("emailSenderService"),
        NotificationType.SMS, senderStrategies.get("smsSenderService")
        // Ajoutez PULL ici si vous avez un service pour cela
    );

    return new ContextSenderStrategy(strategyMap);
  }

  @Bean
  public NotificationService notificationService(
      ContextSenderStrategy contextSenderStrategy,
      ServiceAppService serviceAppService,
      NotificationRepository notificationRepository) {
    return new NotificationService(contextSenderStrategy, serviceAppService, notificationRepository);
  }

  @Bean
  public EmailTemplateService emailTemplateService(EmailTemplateRepository emailTemplateRepository) {
    return new EmailTemplateService(emailTemplateRepository);
  }

  @Bean
  public SMSTemplateService smsTemplateService(SMSTemplateRepository smsTemplateRepository) {
    return new SMSTemplateService(smsTemplateRepository);
  }

  @Bean
  public PullTemplateService pullTemplateService(PullTemplateRepository pullTemplateRepository) {
    return new PullTemplateService(pullTemplateRepository);
  }

  @Bean
  public TemplateFactory templateFactory(
      SMSTemplateService smsTemplateService,
      EmailTemplateService emailTemplateService,
      PullTemplateService pullTemplateService,
      ServiceAppService serviceAppService) {
    return new TemplateFactory(smsTemplateService, emailTemplateService, pullTemplateService, serviceAppService);
  }
}