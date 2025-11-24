package notification.service.yowyob.inc.notification.infrastructure.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import notification.service.yowyob.inc.notification.application.port.output.service.EmailSenderServiceInterface;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Component
@AllArgsConstructor
@Slf4j
public class EmailSenderServiceAdapter implements EmailSenderServiceInterface {

  private final TemplateEngine templateEngine;

  @Override
  public void sendEamil(String to, String from, String template, Map<String, String> data, String subject,
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

    // 2. Préparer le contexte pour Thymeleaf
    Context context = new Context();
        context.setVariables(new HashMap<>(data));

    // 3. Traiter le template HTML avec les données
    String htmlBody = templateEngine.process(template, context);

    // 4. Créer et envoyer le message
    try {
      MimeMessage mimeMessage = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
      helper.setFrom(from);
      helper.setTo(to);
      helper.setSubject(subject);
      helper.setText(htmlBody, true); // true indique que le contenu est HTML

      mailSender.send(mimeMessage);
      log.info("Email sent successfully to {}", to);

    } catch (MessagingException e) {
      log.error("Failed to send email to {}", to, e);
      // Dans une vraie application, vous pourriez lancer une exception personnalisée
      // ici
    }
  }
}