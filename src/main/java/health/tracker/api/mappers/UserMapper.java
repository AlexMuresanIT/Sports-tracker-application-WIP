package health.tracker.api.mappers;

import health.tracker.api.domain.DTO.UserDTO;
import health.tracker.api.domain.Entity.User;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface UserMapper {

  @Mapping(target = "id", expression = "java(user.getId())")
  @Mapping(target = "email", expression = "java(user.getEmail())")
  @Mapping(target = "name", expression = "java(user.getFirstName() + ' ' + user.getLastName())")
  @Mapping(target = "password", expression = "java(user.getPassword())")
  UserDTO toDTO(final User user);

  @Mapping(target = "firstName", expression = "java(getFirstNameFromDTO(userDTO.getName()))")
  @Mapping(target = "lastName", expression = "java(getLastNameFromDTO(userDTO.getName()))")
  @Mapping(target = "email", expression = "java(userDTO.getEmail())")
  @Mapping(target = "id", expression = "java(userDTO.getId())")
  @Mapping(target = "password", expression = "java(userDTO.getPassword())")
  User toDomain(final UserDTO userDTO);

  List<UserDTO> toDTO(final List<User> users);

  default String getFirstNameFromDTO(final String fullName) {
    final var name = fullName.split(" ");
    return name.length > 0 ? name[0] : " ";
  }

  default String getLastNameFromDTO(final String fullName) {
    final var name = fullName.split(" ");
    return name.length > 1 ? name[1] : " ";
  }
}
