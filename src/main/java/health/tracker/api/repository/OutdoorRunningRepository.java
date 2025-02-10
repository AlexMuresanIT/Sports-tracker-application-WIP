package health.tracker.api.repository;

import health.tracker.api.domain.Entity.OutdoorRunning;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutdoorRunningRepository extends MongoRepository<OutdoorRunning, String> {
    List<OutdoorRunning> getAllByUserEmail(String userEmail);
}
