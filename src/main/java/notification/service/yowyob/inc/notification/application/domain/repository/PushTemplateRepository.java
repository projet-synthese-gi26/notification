package notification.service.yowyob.inc.notification.application.domain.repository;

import notification.service.yowyob.inc.notification.application.domain.model.PushTemplate;
import reactor.core.publisher.Mono;

public interface PushTemplateRepository {
    Mono<PushTemplate> findById(int templateId);
    Mono<PushTemplate> save(PushTemplate template);
}
