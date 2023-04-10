package com.commitconf.demo;

import java.util.List;
import org.springframework.web.service.annotation.GetExchange;

public interface UserClient {

  @GetExchange("/users")
  List<UserPlaceholder> allUsers();

  record UserPlaceholder(String name) {

  }

}
