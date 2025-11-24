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
  public ContextSenderStrategy contextSenderStrategy(Map<NotificationType, SenderStrategy> senderStrategies) {
    // Spring va automatiquement injecter une Map de tous les beans qui implémentent
    // SenderStrategy.
    // La clé sera le nom du bean (ex: "emailSenderService").
    // Nous devons transformer cela en Map<NotificationType, SenderStrategy>
    // Pour l'instant, nous laissons la logique de mappage au constructeur ou à une
    // méthode d'initialisation.
    // Pour la simplicité, nous allons juste passer la map telle quelle.
    // Une implémentation plus robuste mapperait les noms de bean aux types de
    // notification.
    return new ContextSenderStrategy(senderStrategies);
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
      PullTemplateService pullTemplateService) {
    return new TemplateFactory(smsTemplateService, emailTemplateService, pullTemplateService);
  }
}