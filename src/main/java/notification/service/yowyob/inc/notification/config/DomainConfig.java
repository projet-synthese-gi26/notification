package notification.service.yowyob.inc.notification.config;

import notification.service.yowyob.inc.notification.application.domain.enums.NotificationType;
import notification.service.yowyob.inc.notification.application.domain.repository.*;
import notification.service.yowyob.inc.notification.application.domain.service.*;
import notification.service.yowyob.inc.notification.application.domain.service.sender.ContextSenderStrategy;
import notification.service.yowyob.inc.notification.application.domain.service.sender.EmailSenderService;
import notification.service.yowyob.inc.notification.application.domain.service.sender.PushSenderService;
import notification.service.yowyob.inc.notification.application.domain.service.sender.SMSSenderService;
import notification.service.yowyob.inc.notification.application.domain.service.sender.SenderStrategy;
import notification.service.yowyob.inc.notification.application.domain.service.sender.WhatsappSenderService;
import notification.service.yowyob.inc.notification.application.domain.service.template.EmailTemplateService;
import notification.service.yowyob.inc.notification.application.domain.service.template.PullTemplateService;
import notification.service.yowyob.inc.notification.application.domain.service.template.SMSTemplateService;
import notification.service.yowyob.inc.notification.application.domain.service.template.TemplateFactory;
import notification.service.yowyob.inc.notification.application.domain.service.template.WhatsappTemplateService;
import notification.service.yowyob.inc.notification.application.port.output.service.EmailSenderServiceInterface;
import notification.service.yowyob.inc.notification.application.port.output.service.PushSenderServiceInterface;
import notification.service.yowyob.inc.notification.application.port.output.service.WhatsappSenderServiceInterface;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class DomainConfig {

  @Bean
  public ServiceAppService serviceAppService(
      ServiceAppRepository serviceAppRepository,
      EmailSenderRepository emailSenderRepository,
      SMSSenderRepository smsSenderRepository,
      WhatsappSenderRepository whatsappSenderRepository,
      PushSenderRepository pushSenderRepository) { // New dependency
    return new ServiceAppService(serviceAppRepository, emailSenderRepository, smsSenderRepository,
        whatsappSenderRepository, pushSenderRepository); // Added pushSenderRepository
  }

  @Bean
  public EmailSenderService emailSenderService(
      EmailTemplateRepository emailTemplateRepository,
      EmailSenderRepository emailSenderRepository,
      EmailSenderServiceInterface emailSenderServiceInterface) {
    return new EmailSenderService(emailTemplateRepository, emailSenderRepository, emailSenderServiceInterface);
  }

  @Bean
  public SMSSenderService smsSenderService(SMSTemplateRepository smsTemplateRepository, SMSSenderRepository smsSenderRepository) {
    return new SMSSenderService(smsTemplateRepository, smsSenderRepository);
  }

  @Bean
  public WhatsappSenderService whatsappSenderService(WhatsappTemplateRepository whatsappTemplateRepository,
      WhatsappSenderServiceInterface whatsappSenderServiceInterface,
      WhatsappSenderRepository whatsappSenderRepository) {
    return new WhatsappSenderService(whatsappTemplateRepository, whatsappSenderServiceInterface,
        whatsappSenderRepository);
  }

  @Bean
  public PushSenderService pushSenderService( // New PushSenderService bean
      PushTemplateRepository pushTemplateRepository,
      PushSenderRepository pushSenderRepository,
      PushSenderServiceInterface pushSenderServiceInterface) {
    return new PushSenderService(pushTemplateRepository, pushSenderRepository, pushSenderServiceInterface);
  }

  @Bean
  public ContextSenderStrategy contextSenderStrategy(EmailSenderService emailSenderService,
                                                     SMSSenderService smsSenderService,
                                                     WhatsappSenderService whatsappSenderService,
                                                     PushSenderService pushSenderService) { // Added pushSenderService
    Map<NotificationType, SenderStrategy> strategyMap = Map.of(
        NotificationType.EMAIL, emailSenderService,
        NotificationType.SMS, smsSenderService,
        NotificationType.WHATSAPP, whatsappSenderService,
        NotificationType.PUSH, pushSenderService); // Added PUSH strategy

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
  public WhatsappTemplateService whatsappTemplateService(WhatsappTemplateRepository whatsappTemplateRepository) {
    return new WhatsappTemplateService(whatsappTemplateRepository);
  }

  @Bean
  public TemplateFactory templateFactory(
      SMSTemplateService smsTemplateService,
      EmailTemplateService emailTemplateService,
      PullTemplateService pullTemplateService,
      ServiceAppService serviceAppService,
      WhatsappTemplateService whatsappTemplateService) {
    return new TemplateFactory(smsTemplateService, emailTemplateService, pullTemplateService, serviceAppService,
        whatsappTemplateService);
  }
}