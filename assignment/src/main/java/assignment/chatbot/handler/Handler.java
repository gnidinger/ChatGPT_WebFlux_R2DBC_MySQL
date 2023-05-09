package assignment.chatbot.handler;

import static assignment.chatbot.entity.constant.Category.*;
import static assignment.chatbot.entity.constant.Template.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;

import assignment.chatbot.entity.Message;
import assignment.chatbot.entity.Prompt;
import assignment.chatbot.entity.constant.PromptCategory;
import assignment.chatbot.repository.MessageRepository;
import assignment.chatbot.repository.PromptRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class Handler {

	private final MessageRepository messageRepository;
	private final PromptRepository promptRepository;
	@Value("${chatgpt.api-key}")
	private String API_KEY;
	private static final String suffix = " :)";

	public Mono<ServerResponse> chat(ServerRequest serverRequest) {

		OpenAiService openAiService = new OpenAiService(API_KEY, Duration.ofSeconds(60));

		/*
		 * 사용자가 입력한 메시지를 테이블에 저장
		 */
		Mono<Message> currentMessage = serverRequest.bodyToMono(Message.class)
			.flatMap(messageRepository::save);

		return currentMessage
			.flatMap(message -> {

				final List<ChatMessage> messages = new ArrayList<>();
				final ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(),
					message.getBody());
				messages.add(systemMessage);

				Mono<String> promptMono = tokenizeAndCategorize(message.getBody())
					.map(Object::toString);

				return promptMono.flatMap(prompt -> {

					final ChatMessage promptMessage = new ChatMessage(ChatMessageRole.USER.value(), PREFIX + prompt);
					messages.add(promptMessage);

					ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
						.model("gpt-3.5-turbo")
						.messages(messages)
						.n(1)
						.maxTokens(500)
						.logitBias(new HashMap<>())
						.build();

					return Mono.fromCallable(() -> openAiService.createChatCompletion(chatCompletionRequest))
						.timeout(Duration.ofSeconds(60)) // 타임아웃 설정
						.flatMap(chatCompletion -> {
							/*
							 * 타임아웃이 발생하지 않으면 챗봇의 답변을 메시지 객체로 생성
							 */
							String result = chatCompletion.getChoices().get(0).getMessage().getContent();
							Message response = Message.builder()
								.sender("AVICA")
								.body(result)
								.build();
							return messageRepository.save(response).thenReturn(response);
						})
						.onErrorResume(TimeoutException.class, e -> {
							/*
							 * 타임아웃 발생시 에러메시지 객체 생성 및 저장
							 */
							Message response = Message.builder()
								.sender("AVICA")
								.body(TIMEOUT_PROMPT.get((int)(Math.random() * 5)))
								.build();
							return messageRepository.save(response).thenReturn(response);
						})
						.onErrorResume(RuntimeException.class, e -> {
							/*
							 * 런타임 에러 발생시 에러메시지 출력 및 객체 생성과 저장
							 */
							log.error("Exception occurred:", e);
							Message response = Message.builder()
								.sender("AVICA")
								.body(ERROR_PROMPT.get((int)(Math.random() * 5)))
								.build();
							return messageRepository.save(response).thenReturn(response);
						});
				});
			})
			.flatMap(response -> ServerResponse.ok().bodyValue(response));
	}

	private Mono<String> tokenizeAndCategorize(String str) {

		Map<String, Integer> categoryMap = new LinkedHashMap<>();
		List<String> categories = List.of("GREETING", "ASTAR", "SERVICE", "TECHNOLOGY", "BUSINESS", "SUPPORT",
			"CONTACT");

		for (String category : categories) {
			categoryMap.put(category, 0);
		}

		Set<Character> stringSet = new HashSet<>(
			List.of('은', '는', '이', '가', '에', '의', '도', '을', '를', '좀', '?', '!')
		);
		List<String> indices = new ArrayList<>();

		String[] strings = str.split(" ");

		for (int i = 0; i < strings.length; i++) {
			String s = strings[i];
			if (stringSet.contains(s.charAt(s.length() - 1))) {
				strings[i] = s.substring(0, s.length() - 1);
			}
			if (GREETING.contains(strings[i])) {
				categoryMap.put("GREETING", categoryMap.get("GREETING") + 1);
				indices.add("GREETING");
			} else if (ASTAR.contains(strings[i])) {
				categoryMap.put("ASTAR", categoryMap.get("ASTAR") + 1);
				indices.add("ASTAR");
			} else if (SERVICE.contains(strings[i])) {
				categoryMap.put("SERVICE", categoryMap.get("SERVICE") + 1);
				indices.add("SERVICE");
			} else if (TECHNOLOGY.contains(strings[i])) {
				categoryMap.put("TECHNOLOGY", categoryMap.get("TECHNOLOGY") + 1);
				indices.add("TECHNOLOGY");
			} else if (BUSINESS.contains(strings[i])) {
				categoryMap.put("BUSINESS", categoryMap.get("BUSINESS") + 1);
				indices.add("BUSINESS");
			} else if (SUPPORT.contains(strings[i])) {
				categoryMap.put("SUPPORT", categoryMap.get("SUPPORT") + 1);
				indices.add("SUPPORT");
			} else if (CONTACT.contains(strings[i])) {
				categoryMap.put("CONTACT", categoryMap.get("CONTACT") + 1);
				indices.add("CONTACT");
			}
		}

		int max = Collections.max(categoryMap.values());

		if (max == 0) {
			return Mono.just("");
		}

		return createPrompt(categoryMap);
	}

	private Mono<String> createPrompt(Map<String, Integer> stringIntegerMap) {
		List<String> keyList = new ArrayList<>(stringIntegerMap.keySet());
		List<Integer> valueList = new ArrayList<>(stringIntegerMap.values());

		int sum = valueList.stream()
			.mapToInt(Integer::intValue)
			.sum();

		Flux<Prompt> promptFlux = Flux.empty();
		for (int i = 0; i < valueList.size(); i++) {
			promptFlux = promptFlux.concatWith(
				promptRepository.findRandomPromptByCategory(keyList.get(i), (40 * valueList.get(i)) / sum));
		}

		return promptFlux.map(Prompt::getBody)
			.reduce(new StringBuffer(), StringBuffer::append)
			.map(StringBuffer::toString);
	}

	public Mono<ServerResponse> updatePrompt() {

		return Flux.merge(
			Flux.fromIterable(stringsToList(GREETING_PROMPT))
				.flatMap(body -> createPromptIfNotExist(body, PromptCategory.GREETING)),
			Flux.fromIterable(stringsToList(ASTAR_PROMPT))
				.flatMap(body -> createPromptIfNotExist(body, PromptCategory.ASTAR)),
			Flux.fromIterable(stringsToList(SERVICE_PROMPT))
				.flatMap(body -> createPromptIfNotExist(body, PromptCategory.SERVICE)),
			Flux.fromIterable(stringsToList(TECHNOLOGY_PROMPT))
				.flatMap(body -> createPromptIfNotExist(body, PromptCategory.TECHNOLOGY)),
			Flux.fromIterable(stringsToList(BUSINESS_PROMPT))
				.flatMap(body -> createPromptIfNotExist(body, PromptCategory.BUSINESS)),
			Flux.fromIterable(stringsToList(SUPPORT_PROMPT))
				.flatMap(body -> createPromptIfNotExist(body, PromptCategory.SUPPORT)),
			Flux.fromIterable(stringsToList(CONTACT_PROMPT))
				.flatMap(body -> createPromptIfNotExist(body, PromptCategory.CONTACT))
		).then(ServerResponse.ok().build());
	}

	private List<String> stringsToList(String str) {
		return Arrays.stream(str.split("\t"))
			.filter(s -> !s.isEmpty())
			.collect(Collectors.toList());
	}

	private Mono<Prompt> createPromptIfNotExist(String body, PromptCategory promptCategory) {
		return promptRepository.findByBody(body)
			.switchIfEmpty(Mono.defer(() -> Mono.just(Prompt.builder()
				.promptCategory(promptCategory)
				.body(body)
				.build()
			).flatMap(promptRepository::save)));
	}
}
