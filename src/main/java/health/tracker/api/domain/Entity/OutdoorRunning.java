package health.tracker.api.domain.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("outdoor running")
public class OutdoorRunning {

    @Id
    private String id;

    @Field("User email")
    private String userEmail;

    @Field("Distance")
    private Double distance;

    @Field("Time")
    private String time;

    @Field("Burned calories")
    private Integer burnedCalories;

    @Field("Average speed")
    private Double averageSpeed;

    public OutdoorRunning(String id, String userEmail, Double distance, String time, Integer burnedCalories, Double averageSpeed) {
        this.id = id;
        this.userEmail = userEmail;
        this.distance = distance;
        this.time = time;
        this.burnedCalories = burnedCalories;
        this.averageSpeed = averageSpeed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("OutdoorRunning{");
        sb.append("id='").append(id).append('\'');
        sb.append(", userEmail='").append(userEmail).append('\'');
        sb.append(", distance=").append(distance);
        sb.append(", time='").append(time).append('\'');
        sb.append(", burnedCalories=").append(burnedCalories);
        sb.append(", averageSpeed=").append(averageSpeed);
        sb.append('}');
        return sb.toString();
    }
}
