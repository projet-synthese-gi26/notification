package notification.service.yowyob.inc.notification.application.domain.service;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.UUID;

import notification.service.yowyob.inc.notification.application.domain.model.EmailSender;
import notification.service.yowyob.inc.notification.application.domain.model.SMSSender;
import notification.service.yowyob.inc.notification.application.domain.model.ServiceApp;
import notification.service.yowyob.inc.notification.application.domain.model.WhatsappSender;
import notification.service.yowyob.inc.notification.application.domain.repository.EmailSenderRepository;
import notification.service.yowyob.inc.notification.application.domain.repository.SMSSenderRepository;
import notification.service.yowyob.inc.notification.application.domain.repository.ServiceAppRepository;
import notification.service.yowyob.inc.notification.application.domain.repository.WhatsappSenderRepository;
import notification.service.yowyob.inc.notification.application.port.input.dto.ServiceCreateRequest;
import notification.service.yowyob.inc.notification.application.port.output.dto.ServiceCreateResponse;

@AllArgsConstructor
public class ServiceAppService {

  private final ServiceAppRepository serviceAppRepository;
  private final EmailSenderRepository emailSenderRepository;
  private final SMSSenderRepository smsSenderRepository;
  private final WhatsappSenderRepository whatsappSenderRepository;

  public Mono<ServiceCreateResponse> registerServiceApp(ServiceCreateRequest request) {
    ServiceApp serviceAppToSave = new ServiceApp();
    serviceAppToSave.setName(request.getName());
    serviceAppToSave.setToken(UUID.randomUUID());

    return serviceAppRepository.save(serviceAppToSave)
        .flatMap(savedServiceApp -> {
            Mono<EmailSender> emailSenderMono = emailSenderRepository.save(
                EmailSender.builder()
                    .serverHost(request.getEmailServerHost())
                    .serverPort(request.getEmailServerPort())
                    .username(request.getEmailUsername())
                    .password(request.getEmailPassword())
                    .serviceAppId(savedServiceApp.getServiceId())
                    .build()
            );

            Mono<SMSSender> smsSenderMono = smsSenderRepository.save(
                SMSSender.builder()
                    .serverHost(request.getSmsServerHost())
                    .serverPort(request.getSmsServerPort())
                    .token(request.getSmstoken())
                    .serviceAppId(savedServiceApp.getServiceId())
                    .build()
            );

            Mono<WhatsappSender> whatsappSenderMono = whatsappSenderRepository.save(
                WhatsappSender.builder()
                    .apiUrl(request.getWhatsappApiUrl())
                    .apiTokenInstance(request.getWhatsappApiTokenInstance())
                    .idInstance(request.getWhatsappIdInstance())
                    .serviceAppId(savedServiceApp.getServiceId())
                    .build()
            );

            return Mono.when(emailSenderMono, smsSenderMono, whatsappSenderMono)
                .thenReturn(savedServiceApp);
        })
        .map(savedServiceApp -> {
            ServiceCreateResponse response = new ServiceCreateResponse();
            response.setServiceId(savedServiceApp.getServiceId());
            response.setName(savedServiceApp.getName());
            response.setToken(savedServiceApp.getToken().toString());
            return response;
        });
  }

  public Mono<ServiceApp> getServiceAppByToken(String token) {
    return this.serviceAppRepository.findByToken(UUID.fromString(token));
  }
}

