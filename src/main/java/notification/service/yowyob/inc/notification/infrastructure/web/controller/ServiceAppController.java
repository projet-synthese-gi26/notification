package notification.service.yowyob.inc.notification.infrastructure.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import notification.service.yowyob.inc.notification.application.domain.service.ServiceAppService;
import notification.service.yowyob.inc.notification.application.port.input.dto.ServiceCreateRequest;
import notification.service.yowyob.inc.notification.application.port.output.dto.ServiceCreateResponse;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Tag(name = "Service Management", description = "APIs for registering and managing services")
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/services")
public class ServiceAppController {

  private final ServiceAppService serviceAppService;

  @Operation(summary = "Register a new service", description = "Creates a new service application and its associated SMS and Email sender configurations. Returns the new service's ID and access token.", responses = {
      @ApiResponse(responseCode = "200", description = "Service registered successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceCreateResponse.class)))
  })
  @PostMapping
  public Mono<ResponseEntity<ServiceCreateResponse>> registerServiceApp(
      @RequestBody ServiceCreateRequest serviceCreateRequest) {
    return Mono.fromCallable(() -> serviceAppService.registerServiceApp(serviceCreateRequest))
        .subscribeOn(Schedulers.boundedElastic())
        .map(response -> ResponseEntity.ok().body(response));
  }

}
