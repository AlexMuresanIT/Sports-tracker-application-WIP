package health.tracker.api.repository;

import health.tracker.api.domain.Entity.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
  Optional<User> findByEmail(String email);

  void deleteByEmail(String email);

  Page<User> findAll(Pageable pageable);
}
