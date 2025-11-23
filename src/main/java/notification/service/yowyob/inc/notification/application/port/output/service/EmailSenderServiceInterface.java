package notification.service.yowyob.inc.notification.application.port.output.service;

import java.util.Map;

public interface EmailSenderServiceInterface {
  public void sendEamil(String to, String from, String template, Map<String, String> data, String subject,
      String smtpServer,
      String smtpPort, String username, String password);
}
