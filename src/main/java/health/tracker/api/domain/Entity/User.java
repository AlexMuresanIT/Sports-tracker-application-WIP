package health.tracker.api.domain.Entity;

import health.tracker.api.domain.Gender;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.integration.annotation.Default;

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

  @Field("Awards")
  private List<Award> awards;

  @Field("Phone number")
  private String phoneNumber;

  public User() {}

  @Default
  public User(
      final String id,
      final String firstName,
      final String email,
      final String lastName,
      final String password,
      final Gender gender,
      final Integer age,
      final String phoneNumber) {
    this.id = id;
    this.firstName = firstName;
    this.email = email;
    this.lastName = lastName;
    this.password = password;
    this.gender = gender;
    this.age = age;
    this.awards = new ArrayList<>();
    this.phoneNumber = phoneNumber;
  }

  public User(
      final String id,
      final String firstName,
      final String lastName,
      final String email,
      final String password,
      final Gender gender,
      final Integer age,
      final List<Award> awards,
      final String phoneNumber) {
    this(id, firstName, lastName, email, password, gender, age, phoneNumber);
    this.awards = awards;
    this.phoneNumber = phoneNumber;
  }

  public static class Builder {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Gender gender;
    private Integer age;
    private List<Award> awards;
    private String phoneNumber;

    public Builder(final User user) {
      this.id = user.getId();
      this.firstName = user.getFirstName();
      this.lastName = user.getLastName();
      this.email = user.getEmail();
      this.password = user.getPassword();
      this.gender = user.getGender();
      this.age = user.getAge();
      this.awards = user.getAwards();
      this.phoneNumber = user.getPhoneNumber();
    }

    public Builder id(final String id) {
      this.id = id;
      return this;
    }

    public Builder firstName(final String firstName) {
      this.firstName = firstName;
      return this;
    }

    public Builder lastName(final String lastName) {
      this.lastName = lastName;
      return this;
    }

    public Builder email(final String email) {
      this.email = email;
      return this;
    }

    public Builder password(final String password) {
      this.password = password;
      return this;
    }

    public Builder gender(final Gender gender) {
      this.gender = gender;
      return this;
    }

    public Builder age(final Integer age) {
      this.age = age;
      return this;
    }

    public Builder awards(final List<Award> awards) {
      this.awards = awards;
      return this;
    }

    public Builder phoneNumber(final String phoneNumber) {
      this.phoneNumber = phoneNumber;
      return this;
    }

    public User build() {
      return new User(id, firstName, lastName, email, password, gender, age, awards, phoneNumber);
    }
  }

  public static Builder builder(final User user) {
    return new Builder(user);
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

  public List<Award> getAwards() {
    return awards;
  }

  public void setAwards(List<Award> awards) {
    this.awards = awards;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
}
