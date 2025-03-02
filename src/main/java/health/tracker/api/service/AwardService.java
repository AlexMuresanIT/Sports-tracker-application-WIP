package health.tracker.api.service;

import health.tracker.api.domain.Entity.Award;
import health.tracker.api.repository.AwardRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AwardService {
  private final AwardRepository awardRepository;

  public AwardService(AwardRepository awardRepository) {
    this.awardRepository = awardRepository;
  }

  public Award saveAward(final Award award) {
    return awardRepository.save(award);
  }

  public void saveAwards(List<Award> awards) {
    awardRepository.saveAll(awards);
  }

  public List<Award> findAllAwards() {
    return awardRepository.findAll();
  }
}
