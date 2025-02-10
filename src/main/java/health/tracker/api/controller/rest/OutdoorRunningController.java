package health.tracker.api.controller.rest;

import health.tracker.api.domain.DTO.OutdoorRunningDTO;
import health.tracker.api.domain.Entity.OutdoorRunning;
import health.tracker.api.mappers.OutdoorRunningMapper;
import health.tracker.api.service.OutdoorRunningService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OutdoorRunningController {

    private final OutdoorRunningService outdoorRunningService;
    private final OutdoorRunningMapper outdoorRunningMapper;

    public OutdoorRunningController(final OutdoorRunningService outdoorRunningService, final OutdoorRunningMapper outdoorRunningMapper) {
        this.outdoorRunningService = outdoorRunningService;
        this.outdoorRunningMapper = outdoorRunningMapper;
    }

    @PostMapping("/addRecord")
    public ResponseEntity<String> addOutdoorRunningRecord(@RequestBody final OutdoorRunningDTO outdoorRunning) {
        outdoorRunningService.addOutdoorRunningRecord(outdoorRunningMapper.toEntity(outdoorRunning));
        return ResponseEntity.ok("Successfully added outdoor running record");
    }

    @GetMapping("/getRecord/{recordId}")
    public ResponseEntity<OutdoorRunningDTO> getOutdoorRunningRecord(@PathVariable final String recordId) {
        final var retrievedRecord = outdoorRunningService.getSpecificOutdoorRunningRecord(recordId);
        return retrievedRecord != null ? ResponseEntity.ok(outdoorRunningMapper.toDTO(retrievedRecord)) : ResponseEntity.badRequest().build();
    }

    @GetMapping("/recordsOf/{email}")
    public ResponseEntity<List<OutdoorRunningDTO>> getAllOutdoorRunningRecordsOfAnUser(@PathVariable final String email) {
        final var records = outdoorRunningService.getAllOutdoorRunningRecordsOfAnUser(email);
        return records.isEmpty()
                ? ResponseEntity.ok(outdoorRunningMapper.toDTOs(records))
                : ResponseEntity.badRequest().build();
    }

    @GetMapping("/allRecords")
    public ResponseEntity<List<OutdoorRunningDTO>> getAllOutdoorRunningRecords() {
        return ResponseEntity.ok(outdoorRunningMapper.toDTOs(outdoorRunningService.getAllRecords()));
    }

    @GetMapping("/best/{email}")
    public ResponseEntity<OutdoorRunningDTO> getBestRecordForUser(@PathVariable final String email) throws Exception {
        try {
            final var bestRecord = outdoorRunningService.bestRecordForTheUser(email);
            return ResponseEntity.ok(outdoorRunningMapper.toDTO(bestRecord));
        } catch (final Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
