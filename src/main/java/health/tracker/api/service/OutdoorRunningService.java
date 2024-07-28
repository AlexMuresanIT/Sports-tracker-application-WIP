package health.tracker.api.service;

import health.tracker.api.domain.OutdoorRunning;
import health.tracker.api.exception.NoUserFoundException;
import health.tracker.api.repository.OutdoorRunningRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OutdoorRunningService {

    private final OutdoorRunningRepository outdoorRunningRepository;
    private final UserService userService;

    public OutdoorRunningService(final OutdoorRunningRepository outdoorRunningRepository, final UserService userService) {
        this.outdoorRunningRepository = outdoorRunningRepository;
        this.userService = userService;
    }

    public void addOutdoorRunningRecord(final OutdoorRunning outdoorRunning) {
        try{
            final var user = userService.getByEmail(outdoorRunning.getUserEmail());
            outdoorRunningRepository.save(outdoorRunning);
        }catch (NoUserFoundException e) {
            throw new NoUserFoundException("You cannot add this outdoor running " +
                    "record since there is no user with this email.");
        }
    }

    public List<OutdoorRunning> getAllOutdoorRunningRecordsOfAnUser(final String email) {
        try{
            final var user = userService.getByEmail(email);
            return outdoorRunningRepository.getAllByUserEmail(user.getEmail());
        }catch (NoUserFoundException e){
            throw new NoUserFoundException(e.getMessage());
        }
    }

    public List<OutdoorRunning> getAllRecords() {
        return outdoorRunningRepository.findAll();
    }

    public OutdoorRunning bestRecordForTheUser(final String email) {
        final var records = getAllOutdoorRunningRecordsOfAnUser(email);
        records.sort(getComparator());
        return records.get(0);
    }

    private Comparator<OutdoorRunning> getComparator() {
        return Comparator.comparing(OutdoorRunning::getDistance).reversed();
    }
}
