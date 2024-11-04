package brain.adn.redis;

import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@SuppressWarnings("rawtypes")
@Component
public class ReactiveRedisComponent {

    public final ReactiveRedisOperations<String, Object> redisOperations;

    public ReactiveRedisComponent(ReactiveRedisOperations<String, Object> redisOperations) {
        this.redisOperations = redisOperations;
    }

    /**
     * Set key and value into a hash key
     * @param key key value - must not be null.
     * @param hashKey hash key value -  must not be null.
     * @param val Object value
     * @return Mono of object
     */
    public Mono<Object> set(String key, String hashKey, Object val) {
        return redisOperations.opsForHash().put(key, hashKey, val).map(b -> val);
    }

    /**
     * Set key and value into a hash key with expiration time
     * @param key key value - must not be null.
     * @param hashKey hash key value -  must not be null.
     * @param val Object value
     * @param duration Duration for expiration
     * @return Mono of object
     */
    public Mono<Object> setWithExpiration(String key, String hashKey, Object val, Duration duration) {
        return redisOperations.opsForHash().put(key, hashKey, val)
                .flatMap(success -> redisOperations.expire(key, duration))
                .map(b -> val);
    }

    /**
     * @param key key value - must not be null.
     * @return Flux of Object
     */
    public Flux<Object> get(String key) {
        return redisOperations.opsForHash().values(key);
    }

    /**
     * Get value for given hashKey from hash at key.
     * @param key key value - must not be null.
     * @param hashKey hash key value -  must not be null.
     * @return Object
     */
    public Mono<Object> get(String key, Object hashKey) {
        return redisOperations.opsForHash().get(key, hashKey)
                .switchIfEmpty(Mono.empty()); // Handle the case where the key or hashKey does not exist
    }

    /**
     * Delete a key that contained in a hash key.
     * @param key key value - must not be null.
     * @param hashKey hash key value -  must not be null.
     * @return 1 Success or 0 Error
     */
    public Mono<Long> remove(String key, Object hashKey) {
        return redisOperations.opsForHash().remove(key, hashKey);
    }
}