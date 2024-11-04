package brain.adn.infraestructura.filtro;

import org.springframework.lang.NonNull;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonList;
import static org.springframework.util.CollectionUtils.toMultiValueMap;

public class FiltroHeaderSeguridad implements WebFilter {

	private static final String X_FRAME_OPTIONS = "X-Frame-Options";
	private static final String PRAGMA = "Pragma";
	private static final String X_CONTENT_TYPE_OPTIONS = "X-Content-Type-Options";
	private static final String X_XSS_PROTECTION = "X-XSS-Protection";

	@NonNull
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

		Map<String, List<String>> headers = new HashMap<>();
		headers.put(X_XSS_PROTECTION, singletonList("1; mode=block"));
		headers.put(X_CONTENT_TYPE_OPTIONS, singletonList("nosniff"));
		headers.put(PRAGMA, singletonList("no-cache"));
		headers.put(X_FRAME_OPTIONS, singletonList("SAMEORIGIN"));

		exchange.getResponse().getHeaders().addAll(toMultiValueMap(headers));

        return chain.filter(exchange);
    }

}