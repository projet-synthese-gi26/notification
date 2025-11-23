package notification.service.yowyob.inc.notification.application.domain.repository;

import notification.service.yowyob.inc.notification.application.domain.model.SMSSender;

public interface SMSSenderRepository {
  public SMSSender save(SMSSender smsSender);
}
