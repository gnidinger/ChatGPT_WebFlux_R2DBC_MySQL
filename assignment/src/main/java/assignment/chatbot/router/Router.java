package assignment.chatbot.router;

import static org.springframework.web.reactive.function.server.RouterFunctions.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import assignment.chatbot.handler.Handler;

@Configuration
public class Router {

	@Bean
	public RouterFunction<ServerResponse> chatRoute(Handler handler) {
		return route()
			.POST("/chat", handler::chat)
			.POST("/update", request -> handler.updatePrompt())
			.build();
	}
}
