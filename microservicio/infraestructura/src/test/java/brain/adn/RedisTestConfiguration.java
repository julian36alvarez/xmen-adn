package brain.adn;

import brain.adn.redis.ReactiveRedisComponent;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@TestConfiguration
public class RedisTestConfiguration {

    @Bean
    @Primary
    public ReactiveRedisComponent reactiveRedisComponentMock(ReactiveRedisOperations<String, Object> redisOperations) {
        return new ReactiveRedisComponent(redisOperations) {
            @Override
            public Flux<Object> get(String key) {
                return Flux.empty();
            }

            @Override
            public Mono<Object> set(String key, String hashKey, Object value) {
                return Mono.just(true);
            }

            @Override
            public Mono<Object> setWithExpiration(String key, String hashKey, Object value, Duration duration) {
                return Mono.just(true);
            }

            @Override
            public Mono<Long> remove(String key, Object hashKey) {
                return Mono.just(1L);
            }
        };
    }
}