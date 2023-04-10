package com.commitconf.demo;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class UserController {

  private final UserRepository userRepository;

  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @GetMapping("/users")
  List<User> all() {
    return userRepository.findAll();
  }

  @GetMapping("/users/{id}")
  ResponseEntity<User> byId(@PathVariable Long id) {
    return userRepository.findById(id)
        .map(ResponseEntity::ok)
        .orElseThrow(UserNotFoundException::new);
  }

  @ControllerAdvice
  static class ErrorHandler {

    @ExceptionHandler
    public ProblemDetail handleNotFound(UserNotFoundException e) {
      ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "The user doesn't exist");

      pd.setTitle("User not found");
      pd.setProperty("some-property", "bla, bla, bla");

      return pd;
    }
  }

}

