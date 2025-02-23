package health.tracker.api.service;

import health.tracker.api.domain.Entity.OutdoorRunning;
import health.tracker.api.exception.NoUserFoundException;
import health.tracker.api.repository.OutdoorRunningRepository;
import java.util.Comparator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class OutdoorRunningService {
  private static final Logger LOGGER = LoggerFactory.getLogger(OutdoorRunningService.class);

  private final OutdoorRunningRepository outdoorRunningRepository;
  private final UserService userService;

  public OutdoorRunningService(
      final OutdoorRunningRepository outdoorRunningRepository, final UserService userService) {
    this.outdoorRunningRepository = outdoorRunningRepository;
    this.userService = userService;
  }

  public OutdoorRunning addOutdoorRunningRecord(final OutdoorRunning outdoorRunning) {
    try {
      userService.getByEmail(outdoorRunning.getUserEmail());
      return outdoorRunningRepository.save(outdoorRunning);
    } catch (NoUserFoundException e) {
      LOGGER.info(
          "You cannot add this outdoor running record since there is no user with the email "
              + outdoorRunning.getUserEmail());
    }
    return null;
  }

  public OutdoorRunning getSpecificOutdoorRunningRecord(final String id) {
    final var outdoorRunning = outdoorRunningRepository.findById(id);
    return outdoorRunning.orElse(null);
  }

  public List<OutdoorRunning> getAllOutdoorRunningRecordsOfAnUser(final String email) {
    try {
      final var user = userService.getByEmail(email);
      return outdoorRunningRepository.getAllByUserEmail(user.getEmail());
    } catch (NoUserFoundException e) {
      LOGGER.info("No user found with this email.");
      return List.of();
    }
  }

  public List<OutdoorRunning> getAllRecords() {
    return outdoorRunningRepository.findAll();
  }

  public OutdoorRunning bestRecordForTheUser(final String email) throws Exception {
    final var records = getAllOutdoorRunningRecordsOfAnUser(email);
    if (records.isEmpty()) {
      throw new Exception("No records for the user with email " + email);
    }
    records.sort(getDistanceComparator());
    return records.get(0);
  }

  private Comparator<OutdoorRunning> getDistanceComparator() {
    return Comparator.comparing(OutdoorRunning::getDistance).reversed();
  }
}
