package health.tracker.api.repository;

import health.tracker.api.domain.Entity.OutdoorRunning;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutdoorRunningRepository extends MongoRepository<OutdoorRunning, String> {
  List<OutdoorRunning> getAllByUserEmail(String userEmail);
}
