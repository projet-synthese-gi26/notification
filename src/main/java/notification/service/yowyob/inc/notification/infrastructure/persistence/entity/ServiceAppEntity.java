package notification.service.yowyob.inc.notification.infrastructure.persistence.entity;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class ServiceAppEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int serviceId;
  private String name;
  private UUID token;

  @OneToOne
  SMSSenderEntity smsSender;

  @OneToOne
  EmailSenderEntity emailSender;

  @OneToMany(mappedBy = "serviceApp")
  List<NotificationEntity> notifications;

  @OneToMany(mappedBy = "serviceApp")
  List<EmailTemplateEntity> emailTemplates;

  @OneToMany(mappedBy = "serviceApp")
  List<SMSTemplateEntity> smsTemplates;

  @OneToMany(mappedBy = "serviceApp")
  List<PullTemplateEntity> pullTemplates;
}
