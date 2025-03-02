package health.tracker.api.domain.Entity;

import health.tracker.api.domain.AwardType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("awards")
public record Award(
    @Id String id,
    String name,
    String description,
    String icon,
    AwardType awardType,
    String completionDate) {}
