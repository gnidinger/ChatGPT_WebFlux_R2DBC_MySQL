package assignment.chatbot.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import assignment.chatbot.entity.Message;

public interface MessageRepository extends ReactiveCrudRepository<Message, Long> {
}
