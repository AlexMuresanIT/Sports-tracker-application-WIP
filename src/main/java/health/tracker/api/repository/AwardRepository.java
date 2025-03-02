package health.tracker.api.repository;

import health.tracker.api.domain.Entity.Award;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AwardRepository extends MongoRepository<Award, String> {}
