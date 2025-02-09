package health.tracker.api.controller.rest;

import health.tracker.api.exception.InvalidData;
import health.tracker.api.exception.NoUserFoundException;
import health.tracker.api.domain.User;
import health.tracker.api.domain.UserDTO;
import health.tracker.api.mappers.UserMapper;
import health.tracker.api.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(final UserService userService, final UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDTO user) {
        try{
            userService.addUser(userMapper.toDomain(user));
            return new ResponseEntity<>("User registered successfully.", HttpStatus.OK);
        }catch (InvalidData e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user/id/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable String id) {
        log.info("Getting user by id: {}", id);
        try{
            final var user = userService.getById(id);
            return new ResponseEntity<>(userMapper.toDTO(user), HttpStatus.OK);
        }catch (NoUserFoundException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/user/email/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        log.info("Getting user by email: {}", email);
        try{
            final var user = userService.getByEmail(email);
            return new ResponseEntity<>(userMapper.toDTO(user), HttpStatus.OK);
        }catch (NoUserFoundException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/user/updateAge/{email}/{age}")
    public ResponseEntity<String> updateUserAge(@PathVariable String email, @PathVariable Integer age) {
        try{
            userService.updateUserAge(email, age);
            return new ResponseEntity<>("Age updated.", HttpStatus.OK);
        }catch (NoUserFoundException | InvalidData e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/user/updatePassword/{email}/{password}")
    public ResponseEntity<String> updateUserPassword(@PathVariable String email, @PathVariable String password) {
        try{
            userService.updateUserPassword(email, password);
            return new ResponseEntity<>("Password updated.", HttpStatus.OK);
        }catch (NoUserFoundException | InvalidData e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/user/updateEmail/{email}/{newEmail}")
    public ResponseEntity<String> updateUserEmail(@PathVariable String email, @PathVariable String newEmail) {
        try{
            userService.updateUserEmail(email, newEmail);
            return new ResponseEntity<>("Email updated.", HttpStatus.OK);
        }catch (NoUserFoundException | InvalidData e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable String id) {
        log.info("Deleting user by id: {}", id);
        userService.deleteUserById(id);
        return new ResponseEntity<>("User deleted.", HttpStatus.OK);
    }

    @DeleteMapping("/user/deleteE/{email}")
    public ResponseEntity<String> deleteUserByEmail(@PathVariable String email) {
        log.info("Deleting user by email: {}", email);
        userService.deleteUserByEmail(email);
        return new ResponseEntity<>("User deleted.", HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        log.info("Getting all users");
        return new ResponseEntity<>(userMapper.toDTOs(userService.getAllUsers()), HttpStatus.OK);
    }

    @GetMapping("/logged")
    public ResponseEntity<String> currentUser(@AuthenticationPrincipal UserDetails user) {
        return new ResponseEntity<>("Current role: " + user.getUsername(), HttpStatus.OK);
    }
}
