package notification.service.yowyob.inc.notification.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class PullTemplateEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int templateId;
  private String name;
  private String description;
  private String message;

  @ManyToOne
  private ServiceAppEntity serviceApp;
}
