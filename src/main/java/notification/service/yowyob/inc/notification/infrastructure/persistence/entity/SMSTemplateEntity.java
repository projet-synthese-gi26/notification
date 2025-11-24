package notification.service.yowyob.inc.notification.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class SMSTemplateEntity {

  private String message;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int templateId;
  private String name;
  private String description;

  @ManyToOne
  private ServiceAppEntity serviceApp;
}
