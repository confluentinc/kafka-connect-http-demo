package io.confluent.connect.http.controller;

import io.confluent.connect.http.domain.Message;
import io.confluent.connect.http.repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@Slf4j
public class MessageController {

  private final MessageRepository messageRepository;

  public MessageController(MessageRepository messageRepository) {
    this.messageRepository = messageRepository;
  }

  @PutMapping(path = "/api/messages")
  public ResponseEntity putMessage(@RequestBody String message) {
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(save(message));
  }

  @PostMapping(path = "/api/messages")
  public ResponseEntity createMessage(@RequestBody String message) {
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(save(message));
  }

  @PostMapping(path = "/api/messages/batch")
  public ResponseEntity createMessages(@RequestBody List<String> messages) {
    log.info("MESSAGES RECEIVED: {}", messages);

    // convert to Message
    List<Message> messagesToSave = messages
        .stream()
        .map(Message::new)
        .collect(toList());

    // save
    Iterable<Message> savedMessages = messageRepository.saveAll(messagesToSave);

    // return saved messages (with ID generated)
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(savedMessages);
  }

  @GetMapping(path = "/api/messages")
  public Iterable<Message> getMessages() {
    return messageRepository.findAll();
  }

  private Message save(String message) {
    log.info("MESSAGE RECEIVED: {}", message);
    return messageRepository.save(new Message(message));
  }
}
