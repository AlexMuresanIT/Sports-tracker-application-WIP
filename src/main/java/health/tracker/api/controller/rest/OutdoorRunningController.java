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
        try{
            final var records = outdoorRunningService.getAllOutdoorRunningRecordsOfAnUser(email);
            return ResponseEntity.ok(records);
        }catch (NoUserFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/allRecords")
    public ResponseEntity<List<OutdoorRunning>> getAllOutdoorRunningRecords() {
        final var allRecords = outdoorRunningService.getAllRecords();
        return ResponseEntity.ok(allRecords);
    }
}
