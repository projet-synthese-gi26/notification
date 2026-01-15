package notification.service.yowyob.inc.notification.application.port.output.service;

public interface WhatsappSenderServiceInterface {
  public void sendWhatsappMessage(
      String apiUrl,
      String apiTokenInstance,
      String idInstance,
      String body);
}
