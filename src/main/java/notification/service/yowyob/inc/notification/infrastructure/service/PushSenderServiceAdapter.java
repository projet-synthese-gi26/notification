package notification.service.yowyob.inc.notification.infrastructure.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import lombok.extern.slf4j.Slf4j;
import notification.service.yowyob.inc.notification.application.domain.model.PushSender;
import notification.service.yowyob.inc.notification.application.domain.model.PushTemplate;
import notification.service.yowyob.inc.notification.application.exception.NotificationSendingException;
import notification.service.yowyob.inc.notification.application.port.output.service.PushSenderServiceInterface;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class PushSenderServiceAdapter implements PushSenderServiceInterface {

    private static final Map<Integer, FirebaseApp> firebaseAppCache = new ConcurrentHashMap<>();

    @Override
    public Mono<Void> sendPush(PushSender sender, PushTemplate template, List<String> deviceTokens,
            Map<String, String> data) {
        return Mono.fromRunnable(() -> {
            try {
                FirebaseApp firebaseApp = firebaseAppCache.computeIfAbsent(sender.getServiceAppId(), id -> {
                    try {
                        String jsonCredentials = sender.getServiceAccountJson();
                        InputStream serviceAccount = new ByteArrayInputStream(jsonCredentials.getBytes());

                        FirebaseOptions options = FirebaseOptions.builder()
                                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                                .build();

                        String appName = "firebase-app-" + id;
                        if (FirebaseApp.getApps().stream().anyMatch(app -> app.getName().equals(appName))) {
                            FirebaseApp.getInstance(appName).delete();
                            log.warn("Re-initializing Firebase app {} for serviceId {}", appName, id);
                        }

                        return FirebaseApp.initializeApp(options, appName);
                    } catch (IOException e) {
                        throw new NotificationSendingException("Failed to initialize Firebase app for service " + id,
                                e);
                    }
                });

                FirebaseMessaging messaging = FirebaseMessaging.getInstance(firebaseApp);

                if (deviceTokens == null || deviceTokens.isEmpty()) {
                    log.warn("No device tokens provided for push notification for serviceAppId: {}",
                            sender.getServiceAppId());
                    return; // Stop processing if no tokens
                }

                Message.Builder messageBuilder = Message.builder()
                        .setToken(deviceTokens.get(0)) // Assuming we send to the first device token for simplicity
                        .putAllData(data);

                if (template.getTitle() != null || template.getBody() != null) {
                    messageBuilder.setNotification(com.google.firebase.messaging.Notification.builder()
                            .setTitle(replaceVariables(template.getTitle(), data))
                            .setBody(replaceVariables(template.getBody(), data))
                            .setImage(template.getImageUrl())
                            .build());
                }

                Message message = messageBuilder.build();

                String response = messaging.send(message);
                log.info("Successfully sent Firebase message for serviceAppId {}: {}", sender.getServiceAppId(),
                        response);

            } catch (FirebaseMessagingException e) {
                log.error("Failed to send Firebase push notification for serviceAppId {}", sender.getServiceAppId(), e);
                throw new NotificationSendingException("Failed to send push notification: " + e.getMessage(), e);
            }
        });
    }

    private String replaceVariables(String text, Map<String, String> variables) {
        if (text == null) {
            return null;
        }
        String result = text;
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            result = result.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }
        return result;
    }
}
