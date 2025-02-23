package health.tracker.api.domain;

public enum Gender {
  MALE("male"),
  FEMALE("female");

  private final String name;

  Gender(String name) {
    this.name = name;
  }
}
