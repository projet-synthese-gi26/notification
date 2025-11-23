package notification.service.yowyob.inc.notification.application.domain.service;

import java.util.UUID;

import notification.service.yowyob.inc.notification.application.domain.model.EmailSender;
import notification.service.yowyob.inc.notification.application.domain.model.SMSSender;
import notification.service.yowyob.inc.notification.application.domain.model.ServiceApp;
import notification.service.yowyob.inc.notification.application.domain.repository.EmailSenderRepository;
import notification.service.yowyob.inc.notification.application.domain.repository.SMSSenderRepository;
import notification.service.yowyob.inc.notification.application.domain.repository.ServiceAppRepository;
import notification.service.yowyob.inc.notification.application.port.input.dto.ServiceCreateRequest;
import notification.service.yowyob.inc.notification.application.port.output.dto.ServiceCreateResponse;

public class ServiceAppService {

  ServiceAppRepository serviceAppRepository;
  EmailSenderRepository emailSenderRepository;
  SMSSenderRepository smsSenderRepository;

  public ServiceCreateResponse registerServiceApp(ServiceCreateRequest request) {
    ServiceApp serviceApp = new ServiceApp();
    serviceApp.setName(request.getName());

    // Génération du token
    UUID token = UUID.randomUUID();
    serviceApp.setToken(token);

    serviceApp = this.serviceAppRepository.save(serviceApp);

    EmailSender emailSender = new EmailSender();
    emailSender.setServerHost(request.getEmailServerHost());
    emailSender.setServerPort(request.getEmailServerPort());
    emailSender.setUsername(request.getEmailUsername());
    emailSender.setPassword(request.getEmailPassword());
    this.emailSenderRepository.save(emailSender);

    SMSSender smsSender = new SMSSender();
    smsSender.setServerHost(request.getSmsServerHost());
    smsSender.setServerPort(request.getSmsServerPort());
    smsSender.setToken(request.getSmstoken());
    this.smsSenderRepository.save(smsSender);

    ServiceCreateResponse response = new ServiceCreateResponse();

    response.setServiceId(serviceApp.getServiceId());
    response.setName(serviceApp.getName());
    response.setToken(serviceApp.getToken().toString());

    return response;
  }

  public ServiceApp getServiceAppByToken(String token) {
    return this.serviceAppRepository.findByToken(UUID.fromString(token));
  }

}
