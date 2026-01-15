package notification.service.yowyob.inc.notification.application.domain.service.sender;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import notification.service.yowyob.inc.notification.application.domain.model.EmailSender;
import notification.service.yowyob.inc.notification.application.domain.model.EmailTemplate;
import notification.service.yowyob.inc.notification.application.domain.model.ServiceApp;
import notification.service.yowyob.inc.notification.application.domain.repository.EmailSenderRepository;
import notification.service.yowyob.inc.notification.application.domain.repository.EmailTemplateRepository;
import notification.service.yowyob.inc.notification.application.port.output.service.EmailSenderServiceInterface;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class EmailSenderService implements SenderStrategy {

  EmailTemplateRepository emailTemplateRepository;
  EmailSenderRepository emailSenderRepository;
  EmailSenderServiceInterface emailSenderServiceInterface;

  @Override
  public Mono<Void> execute(ServiceApp serviceApp, int templateId, List<String> to, Map<String, String> data) {
    return emailTemplateRepository.findById(templateId)
        .zipWith(emailSenderRepository.findByServiceApp(serviceApp))
        .flatMap(tuple -> {
            EmailTemplate template = tuple.getT1();
            EmailSender emailSender = tuple.getT2();

            // TODO: emailSenderServiceInterface.sendEmail should be reactive
            return Mono.fromRunnable(() -> this.emailSenderServiceInterface.sendEmail(to, template.getFromEmail(), template.getBodyHtml(), data,
                template.getSubject(), emailSender.getServerHost(), emailSender.getServerPort(), emailSender.getUsername(),
                emailSender.getPassword()));
        });
  }
}
