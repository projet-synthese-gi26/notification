package notification.service.yowyob.inc.notification.infrastructure.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import notification.service.yowyob.inc.notification.application.domain.model.Template;
import notification.service.yowyob.inc.notification.application.domain.service.TemplateFactory;
import notification.service.yowyob.inc.notification.application.port.input.dto.TemplateCreateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Tag(name = "Template Management", description = "APIs for creating and managing notification templates")
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/templates")
public class TemplateController {

  private final TemplateFactory templateFactory;

  @Operation(summary = "Create a new notification template", description = "Creates a new template (EMAIL, SMS, or PULL) for a specific service.", responses = {
      @ApiResponse(responseCode = "201", description = "Template created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Template.class))),
      @ApiResponse(responseCode = "400", description = "Invalid request payload"),
      @ApiResponse(responseCode = "401", description = "Invalid or missing service token")
  })
  @PostMapping
  public Mono<ResponseEntity<Template>> createTemplate(
      @Parameter(description = "Service authentication token", required = true) @RequestHeader("X-Service-Token") String token,
      @RequestBody TemplateCreateRequest request) {

    return Mono.fromCallable(() -> templateFactory.createTemplate(token, request))
        .subscribeOn(Schedulers.boundedElastic())
        .map(createdTemplate -> ResponseEntity.status(HttpStatus.CREATED).body(createdTemplate));
  }
}