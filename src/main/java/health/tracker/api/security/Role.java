package health.tracker.api.security;

import java.util.Arrays;
import java.util.Optional;

public enum Role {
  ADMIN("admin"),
  UNKNOWN("unknown");

  private final String name;

  Role(String name) {
    this.name = name;
  }

  public Optional<String> getName(String namE) {
    final var found =
        Arrays.stream(Role.values())
            .filter(r -> r.name.equals(namE))
            .findFirst()
            .orElse(Role.UNKNOWN);
    return Optional.of(found.name);
  }
}
