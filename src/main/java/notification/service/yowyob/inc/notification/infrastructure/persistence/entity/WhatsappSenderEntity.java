package notification.service.yowyob.inc.notification.infrastructure.persistence.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("whatsapp_sender")
public class WhatsappSenderEntity {
    @Id
    private Integer whatsappSenderId;
    private String idInstance;
    private String apiTokenInstance;
    private String apiUrl;
    private Integer serviceAppId;
}