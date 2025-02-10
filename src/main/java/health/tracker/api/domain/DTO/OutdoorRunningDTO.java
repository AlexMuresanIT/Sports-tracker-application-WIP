package health.tracker.api.domain.DTO;

import health.tracker.api.domain.Status;

public class OutdoorRunningDTO {
    private String id;
    private String email;
    private Double distance;
    private String time;
    private Integer burnedCalories;
    private Double averageSpeed;
    private Status status;

    public OutdoorRunningDTO(String id, String email, Double distance, String time, Integer burnedCalories, Double averageSpeed, Status status) {
        this.id = id;
        this.email = email;
        this.distance = distance;
        this.time = time;
        this.burnedCalories = burnedCalories;
        this.averageSpeed = averageSpeed;
        this.status = status;
    }
}
