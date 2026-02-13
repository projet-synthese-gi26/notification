package notification.service.yowyob.inc.notification.application.domain.service.sender;

import lombok.AllArgsConstructor;
import notification.service.yowyob.inc.notification.application.domain.model.EmailSender;
import notification.service.yowyob.inc.notification.application.domain.model.EmailTemplate;
import notification.service.yowyob.inc.notification.application.domain.model.ServiceApp;
import notification.service.yowyob.inc.notification.application.domain.repository.EmailSenderRepository;
import notification.service.yowyob.inc.notification.application.domain.repository.EmailTemplateRepository;
import notification.service.yowyob.inc.notification.application.exception.ResourceNotFoundException;
import notification.service.yowyob.inc.notification.application.port.output.service.EmailSenderServiceInterface;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class EmailSenderService implements SenderStrategy {

    EmailTemplateRepository emailTemplateRepository;
    EmailSenderRepository emailSenderRepository;
    EmailSenderServiceInterface emailSenderServiceInterface;

    @Override
    public Mono<Void> execute(ServiceApp serviceApp, int templateId, List<String> to, Map<String, String> data) {

        Mono<EmailTemplate> templateMono = emailTemplateRepository.findById(templateId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Email template not found")));

        Mono<EmailSender> senderMono = emailSenderRepository.findByServiceApp(serviceApp)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Email sender configuration not found")));

        return templateMono
                .zipWith(senderMono)
                .flatMap(tuple -> {
                    EmailTemplate template = tuple.getT1();
                    EmailSender emailSender = tuple.getT2();

                    // TODO: emailSenderServiceInterface.sendEmail should be reactive
                    return Mono.fromRunnable(() -> this.emailSenderServiceInterface.sendEmail(to,
                            template.getFromEmail(), template.getBodyHtml(), data,
                            template.getSubject(), emailSender.getServerHost(), emailSender.getServerPort(),
                            emailSender.getUsername(),
                            emailSender.getPassword()));
                });
    }
}
