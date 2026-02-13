package notification.service.yowyob.inc.notification.infrastructure.web.controller.advice;

import notification.service.yowyob.inc.notification.application.exception.InvalidRequestArgumentException;
import notification.service.yowyob.inc.notification.application.exception.NotificationSendingException;
import notification.service.yowyob.inc.notification.application.exception.ResourceNotFoundException;
import notification.service.yowyob.inc.notification.application.exception.UnsupportedNotificationTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class AdviceController {

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<ProblemDetail> handleResponseStatusException(
      ResponseStatusException ex,
      WebRequest request) {

    ProblemDetail problem = ProblemDetail.forStatus(ex.getStatusCode());
    problem.setTitle(ex.getStatusCode().toString());
    problem.setDetail(ex.getReason());
    problem.setProperty("timestamp", LocalDateTime.now());
    problem.setProperty("path", request.getDescription(false));

    return ResponseEntity
        .status(ex.getStatusCode())
        .body(problem);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<Map<String, String>> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    return ResponseEntity.badRequest().body(errors);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ProblemDetail> handleResourceNotFoundException(
          ResourceNotFoundException ex,
          WebRequest request) {
    ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
    problem.setTitle("Resource Not Found");
    problem.setDetail(ex.getMessage());
    problem.setProperty("timestamp", LocalDateTime.now());
    problem.setProperty("path", request.getDescription(false));
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
  }

  @ExceptionHandler({InvalidRequestArgumentException.class, UnsupportedNotificationTypeException.class})
  public ResponseEntity<ProblemDetail> handleInvalidRequestArgumentException(
          RuntimeException ex,
          WebRequest request) {
    ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
    problem.setTitle("Bad Request");
    problem.setDetail(ex.getMessage());
    problem.setProperty("timestamp", LocalDateTime.now());
    problem.setProperty("path", request.getDescription(false));
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
  }

  @ExceptionHandler(NotificationSendingException.class)
  public ResponseEntity<ProblemDetail> handleNotificationSendingException(
          NotificationSendingException ex,
          WebRequest request) {
    ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    problem.setTitle("Notification Sending Failed");
    problem.setDetail(ex.getMessage());
    problem.setProperty("timestamp", LocalDateTime.now());
    problem.setProperty("path", request.getDescription(false));
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problem);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ProblemDetail> handleAllUncaughtException(
          Exception exception,
          WebRequest request){
    ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    problem.setTitle("Internal Server Error");
    problem.setDetail("An unexpected error occurred");
    problem.setProperty("timestamp", LocalDateTime.now());
    problem.setProperty("path", request.getDescription(false));
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problem);
  }
}
