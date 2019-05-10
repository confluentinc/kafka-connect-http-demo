/**
 * Copyright 2019 Confluent Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/

package io.confluent.connect.http.controller;

import io.confluent.connect.http.producer.TombstoneProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TombstoneController {

  private final TombstoneProducer tombstoneProducer;

  public TombstoneController(TombstoneProducer tombstoneProducer) {
    this.tombstoneProducer = tombstoneProducer;
  }

  @PostMapping(path = "/api/tombstone")
  public ResponseEntity sendTombstone(@RequestParam(defaultValue = "test-topic") String topic,
                                      @RequestParam(defaultValue = "1") String key) {

    // send tombstone to topic with provided id
    tombstoneProducer.send(topic, key);

    return ResponseEntity
        .ok()
        .build();
  }
}
