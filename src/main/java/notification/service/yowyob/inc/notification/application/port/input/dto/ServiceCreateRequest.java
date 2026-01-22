package notification.service.yowyob.inc.notification.application.port.input.dto;

import com.google.firebase.database.annotations.NotNull;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ServiceCreateRequest {
  @NotNull("Service name cannot be null")
  @NotBlank(message = "Service name cannot be blank")
  private String name;

  // Information d'envois de SMS et de l'email

  private String emailServerHost;
  private String emailServerPort;
  private String emailUsername;
  private String emailPassword;

  // SMS
  private String smsServerHost;
  private String smsServerPort;
  private String smstoken;

  // Whatsapp
  private String whatsappApiUrl;
  private String whatsappIdInstance;
  private String whatsappApiTokenInstance;

  // Firebase Push Notification
  private String firebaseServiceAccountJson;
}
