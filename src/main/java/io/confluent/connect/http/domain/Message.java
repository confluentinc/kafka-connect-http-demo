package io.confluent.connect.http.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.parameters.P;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Message {
  public Message(String message) {
    this.message = message;
  }

  @Id
  @GeneratedValue
  private Long id;
  private String message;
}
