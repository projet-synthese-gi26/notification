package notification.service.yowyob.inc.notification.infrastructure.service;

import lombok.AllArgsConstructor;
import notification.service.yowyob.inc.notification.application.exception.NotificationSendingException;
import notification.service.yowyob.inc.notification.application.port.output.service.WhatsappSenderServiceInterface;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class WhatsappSenderServiceAdapter implements WhatsappSenderServiceInterface {

    private final WebClient.Builder webClientBuilder;

    @Override
    public Mono<Void> sendWhatsappMessage(String apiUrl, String apiTokenInstance, String idInstance, String body, List<String> to) {
        String normalizedApiUrl = apiUrl.replaceFirst("^(http[s]?://)", ""); // Remove http(s)://
        normalizedApiUrl = normalizedApiUrl.replaceAll("/$", ""); // Remove trailing slash

        String url = "https://" + normalizedApiUrl + "/waInstance" + idInstance + "/sendMessage/" + apiTokenInstance;
        WebClient webClient = webClientBuilder.build();

        return Flux.fromIterable(to)
                .flatMap(recipient -> {
                    String chatId = recipient + "@c.us";
                    Map<String, String> requestBody = Map.of("chatId", chatId, "message", body);
                    return webClient.post()
                            .uri(url)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(requestBody)
                            .retrieve()
                            .bodyToMono(Void.class)
                            .onErrorMap(
                                WebClientResponseException.class,
                                e -> new NotificationSendingException("Failed to send WhatsApp message: " + e.getResponseBodyAsString(), e)
                            );
                })
                .then();
    }
}
