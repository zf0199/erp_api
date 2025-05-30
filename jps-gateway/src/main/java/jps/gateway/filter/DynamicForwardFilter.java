package jps.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class DynamicForwardFilter implements GlobalFilter, Ordered {

	@Autowired
	private StringRedisTemplate redisTemplate;

	private final WebClient webClient = WebClient.builder().build();

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		ServerHttpRequest request = exchange.getRequest();
		HttpMethod method = request.getMethod();
		String token = request.getHeaders().getFirst("authorization");

		if (!StringUtils.hasText(token) || !token.contains("/")) {
			return chain.filter(exchange);
		}

		String[] parts = token.split("/");
		if (parts.length != 2) {
			return chain.filter(exchange);
		}

		String realToken = parts[0];
		String redisKey = parts[1];
		String targetBaseUrl = redisTemplate.opsForValue().get(redisKey);

		if (!StringUtils.hasText(targetBaseUrl)) {
			return chain.filter(exchange);
		}

		// 构造目标 URI
		URI originalUri = request.getURI();
		String path = originalUri.getPath();
		String query = originalUri.getRawQuery();
		String targetUrl = "http://"+targetBaseUrl + ":9999/"+path + (query != null ? "?" + query : "");

		WebClient.RequestBodySpec forwardRequest = webClient
				.method(method)
				.uri(targetUrl)
				.headers(headers -> {
					headers.addAll(request.getHeaders());
					headers.remove("authorization");
					headers.set("authorization", realToken);
				});

		Mono<String> responseMono = request.getBody()
				.collectList()
				.flatMap(dataBuffers -> {
					// 如果没有 body，就直接发送空 body
					if (dataBuffers.isEmpty()) {
						return forwardRequest.retrieve().bodyToMono(String.class);
					}
					return forwardRequest
							.contentType(MediaType.APPLICATION_JSON)
							.body(BodyInserters.fromDataBuffers(Mono.just(dataBuffers.get(0))))
							.retrieve()
							.bodyToMono(String.class);
				});

		return responseMono.flatMap(responseBody -> {
			ServerHttpResponse response = exchange.getResponse();
			byte[] bytes = responseBody.getBytes();
			response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
			return response.writeWith(Mono.just(response.bufferFactory().wrap(bytes)));
		}).onErrorResume(e -> {
			ServerHttpResponse response = exchange.getResponse();
			response.setStatusCode(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			byte[] bytes = ("Proxy error: " + e.getMessage()).getBytes();
			return response.writeWith(Mono.just(response.bufferFactory().wrap(bytes)));
		});
	}

	@Override
	public int getOrder() {
		return -100; // 优先执行
	}
}
