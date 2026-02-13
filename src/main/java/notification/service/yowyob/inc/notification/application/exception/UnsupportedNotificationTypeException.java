package notification.service.yowyob.inc.notification.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnsupportedNotificationTypeException extends RuntimeException {
    public UnsupportedNotificationTypeException(String message) {
        super(message);
    }
}
