package notification.service.yowyob.inc.notification.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class EmailTemplateEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String name;
  private String description;
  private String fromEmail;
  private String subject;
  private String bodyHtml;

  @ManyToOne
  private ServiceAppEntity serviceApp;
}
