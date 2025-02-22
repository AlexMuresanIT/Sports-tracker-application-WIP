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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getBurnedCalories() {
        return burnedCalories;
    }

    public void setBurnedCalories(Integer burnedCalories) {
        this.burnedCalories = burnedCalories;
    }

    public Double getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(Double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
