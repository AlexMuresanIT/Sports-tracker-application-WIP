package health.tracker.api.controller.rest;

import health.tracker.api.domain.OutdoorRunning;
import health.tracker.api.exception.NoUserFoundException;
import health.tracker.api.service.OutdoorRunningService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OutdoorRunningController {

    private final OutdoorRunningService outdoorRunningService;

    public OutdoorRunningController(OutdoorRunningService outdoorRunningService) {
        this.outdoorRunningService = outdoorRunningService;
    }

    @PostMapping("/addRecord")
    public ResponseEntity<String> addOutdoorRunningRecord(@RequestBody final OutdoorRunning outdoorRunning) {
        outdoorRunningService.addOutdoorRunningRecord(outdoorRunning);
        return ResponseEntity.ok("Successfully added outdoor running record");
    }

    @GetMapping("/recordsOf/{email}")
    public ResponseEntity<List<OutdoorRunning>> getAllOutdoorRunningRecordsOfAnUser(@PathVariable final String email) {
        final var records = outdoorRunningService.getAllOutdoorRunningRecordsOfAnUser(email);
        return records.isEmpty()
                ? ResponseEntity.ok(records)
                : ResponseEntity.badRequest().build();
    }

    @GetMapping("/allRecords")
    public ResponseEntity<List<OutdoorRunning>> getAllOutdoorRunningRecords() {
        final var allRecords = outdoorRunningService.getAllRecords();
        return ResponseEntity.ok(allRecords);
    }

    @GetMapping("/best/{email}")
    public ResponseEntity<OutdoorRunning> getBestRecordForUser(@PathVariable final String email) throws Exception {
        try {
            final var bestRecord = outdoorRunningService.bestRecordForTheUser(email);
            return ResponseEntity.ok(bestRecord);
        } catch (final Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
