package notification.service.yowyob.inc.notification.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PushTemplate extends Template {
  private String title;
  private String body;
  private String imageUrl; // Optional image URL for the push notification
  private String clickAction; // Action to perform when the notification is clicked
}
