package health.tracker.api.security;

import java.util.Optional;

public enum Role {
  ADMIN("admin"),
  USER("user"),
  UNKNOWN("unknown");

  private final String name;

  Role(final String name) {
    this.name = name;
  }

  public Role getName(final String name) {
    return Optional.of(Role.valueOf(name)).orElse(Role.ADMIN);
  }
}
