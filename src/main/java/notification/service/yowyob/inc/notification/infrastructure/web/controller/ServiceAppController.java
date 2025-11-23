package notification.service.yowyob.inc.notification.infrastructure.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import notification.service.yowyob.inc.notification.application.domain.service.ServiceAppService;
import notification.service.yowyob.inc.notification.application.port.input.dto.ServiceCreateRequest;
import notification.service.yowyob.inc.notification.application.port.output.dto.ServiceCreateResponse;

@RestController
@AllArgsConstructor
@RequestMapping("/service")
public class ServiceAppController {

  ServiceAppService serviceAppService;

  @PostMapping("/register")
  public ResponseEntity<ServiceCreateResponse> registerServiceApp(
      @RequestBody ServiceCreateRequest serviceCreateRequest) {
    return ResponseEntity.ok().body(serviceAppService.registerServiceApp(serviceCreateRequest));
  }

}
