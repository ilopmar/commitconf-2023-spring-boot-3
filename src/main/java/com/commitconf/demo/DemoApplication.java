package com.commitconf.demo;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@SpringBootApplication
public class DemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

  @Bean
  CommandLineRunner clr(UserRepository repository, UserClient userClient) {
    return args -> {
      if (repository.findAll().isEmpty()) {
        userClient.allUsers().forEach(userPlaceholder -> {
          System.out.println("Storing user: " + userPlaceholder.name());
          repository.save(new User(null, userPlaceholder.name()));
        });
      }
    };
  }

  @Bean
  UserClient client(WebClient.Builder builder) {
    var webClient = builder.baseUrl("https://jsonplaceholder.typicode.com").build();
    var webClientAdapter = WebClientAdapter.forClient(webClient);

    return HttpServiceProxyFactory
        .builder(webClientAdapter)
        .build()
        .createClient(UserClient.class);
  }

  @Bean
  ObservedAspect observedAspect(ObservationRegistry observationRegistry) {
    return new ObservedAspect(observationRegistry);
  }

}
