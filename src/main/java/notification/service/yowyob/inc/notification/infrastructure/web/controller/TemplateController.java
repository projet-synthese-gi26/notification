package notification.service.yowyob.inc.notification.infrastructure.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import notification.service.yowyob.inc.notification.application.domain.enums.NotificationType;
import notification.service.yowyob.inc.notification.application.domain.model.Template;
import notification.service.yowyob.inc.notification.application.domain.service.template.TemplateFactory;
import notification.service.yowyob.inc.notification.application.port.input.dto.TemplateCreateRequest;
import notification.service.yowyob.inc.notification.application.port.input.dto.TemplatePreview;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Tag(name = "Template Management", description = "APIs for creating and managing notification templates")
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/templates")
public class TemplateController {

  private final TemplateFactory templateFactory;

  @Operation(summary = "Create a new notification template", description = "Creates a new template (EMAIL, SMS, WHATSAPP, or PULL) for a specific service.", responses = {
      @ApiResponse(responseCode = "201", description = "Template created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Template.class))),
      @ApiResponse(responseCode = "400", description = "Invalid request payload"),
      @ApiResponse(responseCode = "401", description = "Invalid or missing service token")
  })
  @PostMapping
  public Mono<ResponseEntity<Template>> createTemplate(
      @Parameter(description = "Service authentication token", required = true) @RequestHeader("X-Service-Token") String token,
      @RequestBody TemplateCreateRequest request) {

    return templateFactory.createTemplate(token, request)
        .map(createdTemplate -> ResponseEntity.status(HttpStatus.CREATED).body(createdTemplate));
  }

  @Operation(summary = "Get preview of all templates for a service", description = "Returns a list of all templates (name, description, id) associated with the provided service token.", responses = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved template previews", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TemplatePreview.class))),
      @ApiResponse(responseCode = "401", description = "Invalid or missing service token")
  })
  @GetMapping("/previews")
  public Flux<TemplatePreview> getTemplatePreviews(
      @Parameter(description = "Service authentication token", required = true) @RequestHeader("X-Service-Token") String token) {
    return templateFactory.findAllTemplatesByServiceAppId(token);
  }

  @Operation(summary = "Get a template by ID", description = "Returns a single template based on its ID and type.", responses = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved template", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Template.class))),
      @ApiResponse(responseCode = "400", description = "Invalid template type or ID"),
      @ApiResponse(responseCode = "404", description = "Template not found")
  })
  @GetMapping("/{id}")
  public Mono<ResponseEntity<Template>> getTemplateById(
      @Parameter(description = "Service authentication token", required = true) @RequestHeader("X-Service-Token") String token,
      @Parameter(description = "ID of the template to retrieve", required = true) @PathVariable Integer id,
      @Parameter(description = "Type of the template (EMAIL, SMS, WHATSAPP, PULL)", required = true) @RequestParam NotificationType type) {
    return templateFactory.getTemplateById(token, id, type)
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @Operation(summary = "Update a template by ID", description = "Updates an existing template identified by its ID and type.", responses = {
      @ApiResponse(responseCode = "200", description = "Template updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Template.class))),
      @ApiResponse(responseCode = "400", description = "Invalid request payload or template type"),
      @ApiResponse(responseCode = "401", description = "Invalid or missing service token"),
      @ApiResponse(responseCode = "404", description = "Template not found")
  })
  @PatchMapping("/{id}")
  public Mono<ResponseEntity<Template>> patchTemplate(
      @Parameter(description = "Service authentication token", required = true) @RequestHeader("X-Service-Token") String token,
      @Parameter(description = "ID of the template to update", required = true) @PathVariable Integer id,
      @Parameter(description = "Type of the template (EMAIL, SMS, WHATSAPP, PULL)", required = true) @RequestParam NotificationType type,
      @RequestBody TemplateCreateRequest request) {
    return templateFactory.updateTemplate(token, id, type, request)
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @Operation(summary = "Delete a template by ID", description = "Deletes a template identified by its ID and type.", responses = {
      @ApiResponse(responseCode = "204", description = "Template deleted successfully"),
      @ApiResponse(responseCode = "400", description = "Invalid template type or ID"),
      @ApiResponse(responseCode = "401", description = "Invalid or missing service token"),
      @ApiResponse(responseCode = "404", description = "Template not found")
  })
  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<Void>> deleteTemplate(
      @Parameter(description = "Service authentication token", required = true) @RequestHeader("X-Service-Token") String token,
      @Parameter(description = "ID of the template to delete", required = true) @PathVariable Integer id,
      @Parameter(description = "Type of the template (EMAIL, SMS, WHATSAPP, PULL)", required = true) @RequestParam NotificationType type) {
    return templateFactory.deleteTemplate(token, id, type)
        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
        .defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
  }
}