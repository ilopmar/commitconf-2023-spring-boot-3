package com.commitconf.demo;

import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

  @Bean
  CommandLineRunner clr(UserRepository repository) {
    return args -> {
      if (repository.findAll().isEmpty()) {
        List.of("Sheldon", "Leonard", "Raj", "Howard").forEach(name -> {
          System.out.println("Storing user: " + name);
          repository.save(new User(null, name));
        });
      }
    };
  }

}
