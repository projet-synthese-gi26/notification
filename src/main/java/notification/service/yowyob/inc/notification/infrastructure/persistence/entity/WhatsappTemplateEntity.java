package notification.service.yowyob.inc.notification.infrastructure.persistence.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("whatsapp_template")
public class WhatsappTemplateEntity {
    @Id
    private Integer templateId;
    private String body;
    private String name;
    private String description;
    private Integer serviceAppId;
}
