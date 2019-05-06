package io.confluent.connect.http.repository;

import io.confluent.connect.http.domain.Message;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends PagingAndSortingRepository<Message, Long> {}
