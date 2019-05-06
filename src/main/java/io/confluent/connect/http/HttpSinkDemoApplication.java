package io.confluent.connect.http;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HttpSinkDemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(HttpSinkDemoApplication.class, args);
  }

}
