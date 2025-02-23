package health.tracker.api.domain.Entity;

import health.tracker.api.domain.Gender;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("users")
public class User {

  @Id private String id;

  @Field("First Name")
  private String firstName;

  @Field("Last Name")
  private String lastName;

  @Field("Email")
  @Indexed(unique = true)
  private String email;

  @Field("Password")
  private String password;

  @Field("Gender")
  private Gender gender;

  @Field("Age")
  private Integer age;

  public User(
      String id,
      String firstName,
      String email,
      String lastName,
      String password,
      Gender gender,
      Integer age) {
    this.id = id;
    this.firstName = firstName;
    this.email = email;
    this.lastName = lastName;
    this.password = password;
    this.gender = gender;
    this.age = age;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }
}
