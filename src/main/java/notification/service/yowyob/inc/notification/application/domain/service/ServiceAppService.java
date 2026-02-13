package notification.service.yowyob.inc.notification.application.domain.service;

import lombok.AllArgsConstructor;
import notification.service.yowyob.inc.notification.application.domain.model.*;
import notification.service.yowyob.inc.notification.application.domain.repository.*;
import notification.service.yowyob.inc.notification.application.exception.InvalidRequestArgumentException;
import notification.service.yowyob.inc.notification.application.exception.ResourceNotFoundException;
import notification.service.yowyob.inc.notification.application.port.input.dto.ServiceAppUpdateRequest;
import notification.service.yowyob.inc.notification.application.port.input.dto.ServiceCreateRequest;
import notification.service.yowyob.inc.notification.application.port.output.dto.ServiceCreateResponse;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class ServiceAppService {

  private final ServiceAppRepository serviceAppRepository;
  private final EmailSenderRepository emailSenderRepository;
  private final SMSSenderRepository smsSenderRepository;
  private final WhatsappSenderRepository whatsappSenderRepository;
  private final PushSenderRepository pushSenderRepository; // New dependency

  @Transactional
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

            Mono<PushSender> pushSenderMono = pushSenderRepository.save( // New PushSender creation
                PushSender.builder()
                    .serviceAccountJson(request.getFirebaseServiceAccountJson())
                    .serviceAppId(savedServiceApp.getServiceId())
                    .build()
            );

            return Mono.when(emailSenderMono, smsSenderMono, whatsappSenderMono, pushSenderMono) // Added pushSenderMono
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
        return Mono.fromCallable(() -> UUID.fromString(token))
                .onErrorMap(IllegalArgumentException.class, e -> new InvalidRequestArgumentException("Invalid token format"))
                .flatMap(serviceAppRepository::findByToken)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Service app not found for the given token")));
    }


    @Transactional
    public Mono<Void> updateServiceApp(String serviceToken, ServiceAppUpdateRequest request) {
        return serviceAppRepository.findByToken(UUID.fromString(serviceToken))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Service app not found")))
                .flatMap(serviceApp -> {
                    List<Mono<Void>> updateMonos = new ArrayList<>();

                    if (request.getEmailSender() != null) {
                        updateMonos.add(
                                emailSenderRepository.findByServiceAppId(serviceApp.getServiceId())
                                                                                 .flatMap(emailSender -> {
                                                                                     if (StringUtils.hasText(request.getEmailSender().getServerHost())) {
                                                                                         emailSender.setServerHost(request.getEmailSender().getServerHost());
                                                                                     }
                                                                                     if (StringUtils.hasText(request.getEmailSender().getServerPort())) {
                                                                                         emailSender.setServerPort(request.getEmailSender().getServerPort());
                                                                                     }
                                                                                     if (StringUtils.hasText(request.getEmailSender().getUsername())) {
                                                                                         emailSender.setUsername(request.getEmailSender().getUsername());
                                                                                     }
                                                                                     if (StringUtils.hasText(request.getEmailSender().getPassword())) {
                                                                                         emailSender.setPassword(request.getEmailSender().getPassword());
                                                                                     }
                                                                                     return emailSenderRepository.save(emailSender).then();
                                                                                 })                        );
                    }

                    if (request.getSmsSender() != null) {
                        updateMonos.add(
                                smsSenderRepository.findByServiceAppId(serviceApp.getServiceId())
                                                                                 .flatMap(smsSender -> {
                                                                                     if (StringUtils.hasText(request.getSmsSender().getServerHost())) {
                                                                                         smsSender.setServerHost(request.getSmsSender().getServerHost());
                                                                                     }
                                                                                     if (StringUtils.hasText(request.getSmsSender().getServerPort())) {
                                                                                         smsSender.setServerPort(request.getSmsSender().getServerPort());
                                                                                     }
                                                                                     if (StringUtils.hasText(request.getSmsSender().getToken())) {
                                                                                         smsSender.setToken(request.getSmsSender().getToken());
                                                                                     }
                                                                                     return smsSenderRepository.save(smsSender).then();
                                                                                 })                        );
                    }

                    if (request.getPushSender() != null) {
                        updateMonos.add(
                                pushSenderRepository.findByServiceAppId(serviceApp.getServiceId())
                                                                                 .flatMap(pushSender -> {
                                                                                     if (StringUtils.hasText(request.getPushSender().getServiceAccountJson())) {
                                                                                         pushSender.setServiceAccountJson(request.getPushSender().getServiceAccountJson());
                                                                                     }
                                                                                     return pushSenderRepository.save(pushSender).then();
                                                                                 })                        );
                    }

                    if (request.getWhatsappSender() != null) {
                        updateMonos.add(
                                whatsappSenderRepository.findByServiceAppId(serviceApp.getServiceId())
                                                                                                                          .flatMap(whatsappSender -> {
                                                                                                                              if (StringUtils.hasText(request.getWhatsappSender().getApiUrl())) {
                                                                                                                                  whatsappSender.setApiUrl(request.getWhatsappSender().getApiUrl());
                                                                                                                              }
                                                                                                                              if (StringUtils.hasText(request.getWhatsappSender().getIdInstance())) {
                                                                                                                                  whatsappSender.setIdInstance(request.getWhatsappSender().getIdInstance());
                                                                                                                              }
                                                                                                                              if (StringUtils.hasText(request.getWhatsappSender().getApiTokenInstance())) {
                                                                                                                                  whatsappSender.setApiTokenInstance(request.getWhatsappSender().getApiTokenInstance());
                                                                                                                              }
                                                                                                                              return whatsappSenderRepository.save(whatsappSender).then();
                                                                                                                          })                        );
                    }

                    return Mono.when(updateMonos);
                });
    }

}