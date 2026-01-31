package notification.service.yowyob.inc.notification.application.port.input.dto;

import lombok.Builder;

@Builder
public record TemplatePreview(String id, String name, String description) {
}
