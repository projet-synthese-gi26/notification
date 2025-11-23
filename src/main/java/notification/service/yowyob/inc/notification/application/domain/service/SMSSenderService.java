package notification.service.yowyob.inc.notification.application.domain.service;

import java.util.Map;

import notification.service.yowyob.inc.notification.application.domain.model.ServiceApp;

public class SMSSenderService implements SenderStrategy {

  @Override
  public void execute(ServiceApp serviceApp, int templateId, String to, Map<String, String> data) {
  }

}