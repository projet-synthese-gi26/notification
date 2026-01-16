package notification.service.yowyob.inc.notification.application.port.input.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceAppUpdateRequest {

    private EmailSenderUpdateRequest emailSender;
    private SMSSenderUpdateRequest smsSender;
    private PushSenderUpdateRequest pushSender;
    private WhatsappSenderUpdateRequest whatsappSender;


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class EmailSenderUpdateRequest {
        private String serverHost;
        private String serverPort;
        private String username;
        private String password;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class SMSSenderUpdateRequest {
        private String serverHost;
        private String serverPort;
        private String token;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class PushSenderUpdateRequest {
        private String serviceAccountJson;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class WhatsappSenderUpdateRequest {
        private String apiUrl;
        private String idInstance;
        private String apiTokenInstance;
    }


}