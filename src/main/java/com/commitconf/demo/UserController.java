package com.commitconf.demo;

import java.util.List;
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
    public ResponseEntity<?> handleNotFound(UserNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

}

