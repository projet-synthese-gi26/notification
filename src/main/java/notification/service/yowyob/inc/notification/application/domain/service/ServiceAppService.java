package notification.service.yowyob.inc.notification.application.domain.service;

import lombok.AllArgsConstructor;
import java.util.UUID;

import notification.service.yowyob.inc.notification.application.domain.model.EmailSender;
import notification.service.yowyob.inc.notification.application.domain.model.SMSSender;
import notification.service.yowyob.inc.notification.application.domain.model.ServiceApp;
import notification.service.yowyob.inc.notification.application.domain.repository.EmailSenderRepository;
import notification.service.yowyob.inc.notification.application.domain.repository.SMSSenderRepository;
import notification.service.yowyob.inc.notification.application.domain.repository.ServiceAppRepository;
import notification.service.yowyob.inc.notification.application.port.input.dto.ServiceCreateRequest;
import notification.service.yowyob.inc.notification.application.port.output.dto.ServiceCreateResponse;

@AllArgsConstructor
public class ServiceAppService {

  private final ServiceAppRepository serviceAppRepository;
  private final EmailSenderRepository emailSenderRepository;
  private final SMSSenderRepository smsSenderRepository;

  public ServiceCreateResponse registerServiceApp(ServiceCreateRequest request) {
    // 1. Créer et sauvegarder le ServiceApp
    ServiceApp serviceAppToSave = new ServiceApp();
    serviceAppToSave.setName(request.getName());
    serviceAppToSave.setToken(UUID.randomUUID());
    ServiceApp savedServiceApp = this.serviceAppRepository.save(serviceAppToSave);

    // 2. Créer et sauvegarder EmailSender, lié au ServiceApp
    EmailSender emailSender = new EmailSender();
    emailSender.setServerHost(request.getEmailServerHost());
    emailSender.setServerPort(request.getEmailServerPort());
    emailSender.setUsername(request.getEmailUsername());
    emailSender.setPassword(request.getEmailPassword());
    emailSender.setServiceAppId(savedServiceApp.getServiceId());
    this.emailSenderRepository.save(emailSender);

    // 3. Créer et sauvegarder SMSSender, lié au ServiceApp
    SMSSender smsSender = new SMSSender();
    smsSender.setServerHost(request.getSmsServerHost());
    smsSender.setServerPort(request.getSmsServerPort());
    smsSender.setToken(request.getSmstoken());
    smsSender.setServiceAppId(savedServiceApp.getServiceId());
    this.smsSenderRepository.save(smsSender);

    // 4. Construire la réponse
    ServiceCreateResponse response = new ServiceCreateResponse();
    response.setServiceId(savedServiceApp.getServiceId());
    response.setName(savedServiceApp.getName());
    response.setToken(savedServiceApp.getToken().toString());
    return response;
  }

  public ServiceApp getServiceAppByToken(String token) {
    return this.serviceAppRepository.findByToken(UUID.fromString(token));
  }

}
