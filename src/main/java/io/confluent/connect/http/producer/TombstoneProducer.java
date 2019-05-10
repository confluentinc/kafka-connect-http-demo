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

package io.confluent.connect.http.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Slf4j
@Component
public class TombstoneProducer {
  private final KafkaProducer<Object, Object> producer;

  public TombstoneProducer() {
    Properties props = new Properties();
    props.put("bootstrap.servers", "localhost:9092");
    props.put("group.id", "tombstone-producer");
    props.put("key.serializer", StringSerializer.class.getName());
    props.put("value.serializer", StringSerializer.class.getName());

    producer = new KafkaProducer<>(props);
  }

  /**
   * Sends a tombstone record for the provided key to the provided topic.
   * @param topic topic to send message to
   * @param key key value
   */
  public void send(String topic, String key) {
    log.info("SENDING TOMBSTONE FOR KEY {} TO THE {} TOPIC", key, topic);
    producer
        .send(new ProducerRecord<>(topic, key, null), (recordMetadata, e) -> {
          if (e == null) {
            log.info("TOMBSTONE SENT");
          } else {
            log.error("TOMBSTONE ERROR", e);
          }
        });
  }
}
