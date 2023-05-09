package assignment.chatbot.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import assignment.chatbot.entity.Prompt;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PromptRepository extends ReactiveCrudRepository<Prompt, Long> {

	@Query("SELECT * FROM prompt WHERE prompt_category = :category ORDER BY RAND() LIMIT :limit")
	Flux<Prompt> findRandomPromptByCategory(@Param("category") String category, @Param("limit") int limit);

	Mono<Prompt> findByBody(String body);
}
