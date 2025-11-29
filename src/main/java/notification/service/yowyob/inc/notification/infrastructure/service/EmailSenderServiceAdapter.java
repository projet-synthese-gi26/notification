package notification.service.yowyob.inc.notification.infrastructure.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import notification.service.yowyob.inc.notification.application.port.output.service.EmailSenderServiceInterface;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Component
@AllArgsConstructor
@Slf4j
public class EmailSenderServiceAdapter implements EmailSenderServiceInterface {

  @Override
  public void sendEamil(List<String> to, String from, String template, Map<String, String> data, String subject,
      String smtpServer, String smtpPort, String username, String password) {

    // 1. Configurer dynamiquement le sender pour chaque envoi
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost(smtpServer);
    mailSender.setPort(Integer.parseInt(smtpPort));
    mailSender.setUsername(username);
    mailSender.setPassword(password);

    Properties props = mailSender.getJavaMailProperties();
    props.put("mail.transport.protocol", "smtp");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.debug", "true"); // Mettre à false en production

    // 2. Remplacer les variables dans le sujet et le corps de l'email
    String processedSubject = replaceVariables(subject, data);
    String htmlBody = replaceVariables(template, data);

    // 3. Créer et envoyer le message
    try {
      MimeMessage mimeMessage = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
      helper.setFrom(from);
      helper.setTo(to.toArray(new String[0]));
      helper.setSubject(processedSubject);
      helper.setText(htmlBody, true); // true indique que le contenu est HTML

      mailSender.send(mimeMessage);
      log.info("Email sent successfully to {}", to);

    } catch (MessagingException e) {
      log.error("Failed to send email to {}", to, e);
      // Dans une vraie application, vous pourriez lancer une exception personnalisée
      // ici
    }
  }

  private String replaceVariables(String templateString, Map<String, String> variables) {
    String result = templateString;
    for (Map.Entry<String, String> entry : variables.entrySet()) {
      result = result.replace("{{" + entry.getKey() + "}}", entry.getValue());
    }
    return result;
  }
}