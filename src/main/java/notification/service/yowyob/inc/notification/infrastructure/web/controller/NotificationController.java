package notification.service.yowyob.inc.notification.infrastructure.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import notification.service.yowyob.inc.notification.application.domain.service.NotificationService;
import notification.service.yowyob.inc.notification.application.port.input.dto.NotificationCreateRequest;
import notification.service.yowyob.inc.notification.application.port.input.dto.NotificationSendRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Tag(name = "Notification Management", description = "APIs for sending and creating notifications")
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/notifications")
public class NotificationController {

  private final NotificationService notificationService;

  @Operation(summary = "Send a notification immediately", description = "Triggers the immediate sending of a notification (e.g., Email, SMS) using a specified template.", responses = {
      @ApiResponse(responseCode = "202", description = "Notification accepted for sending"),
      @ApiResponse(responseCode = "400", description = "Invalid request payload"),
      @ApiResponse(responseCode = "401", description = "Invalid or missing service token")
  })
  @PostMapping("/send")
  public Mono<ResponseEntity<Void>> sendNotification(
      @Parameter(description = "Service authentication token", required = true) @RequestHeader("X-Service-Token") String token,
      @RequestBody NotificationSendRequest request) {

    return Mono.fromRunnable(() -> notificationService.send(
        token,
        request.getNotificationType(),
        request.getTemplateId(),
        request.getTo(),
        request.getData()))
        .subscribeOn(Schedulers.boundedElastic())
        .then(Mono.fromCallable(() -> ResponseEntity.accepted().build()));
  }

  @Operation(summary = "Create a 'pull' notification", description = "Creates a notification record in the database that can be retrieved later by the user (e.g., for an in-app notification center).", responses = {
      @ApiResponse(responseCode = "201", description = "Notification created successfully"),
      @ApiResponse(responseCode = "400", description = "Invalid request payload"),
      @ApiResponse(responseCode = "401", description = "Invalid or missing service token")
  })
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<ResponseEntity<Void>> createNotification(
      @Parameter(description = "Service authentication token", required = true) @RequestHeader("X-Service-Token") String token,
      @RequestBody NotificationCreateRequest request) {

    return Mono.fromRunnable(() -> notificationService.create(
        token,
        request.getNotificationType(),
        request.getTemplateId(),
        request.getUserId(),
        request.getData()))
        .subscribeOn(Schedulers.boundedElastic())
        .then(Mono.fromCallable(() -> ResponseEntity.status(HttpStatus.CREATED).build()));
  }
}