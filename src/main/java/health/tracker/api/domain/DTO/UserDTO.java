package health.tracker.api.domain.DTO;

import health.tracker.api.domain.Gender;

public class UserDTO {

  private String id;
  private String name;
  private String email;
  private Gender gender;
  private Integer age;
  private String password;

  public UserDTO(
      final String id,
      final String name,
      final String email,
      final Gender gender,
      final Integer age,
      final String password) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.gender = gender;
    this.age = age;
    this.password = password;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
