package brain.adn.adn.adaptador.dao;
import brain.adn.adn.adaptador.transformador.TransformadorAdn;
import brain.adn.adn.modelo.dto.DtoEstadisticas;
import brain.adn.adn.puerto.dao.DaoAdn;
import brain.adn.infraestructura.r2dbc.CustomRRDBCTemplate;
import brain.adn.infraestructura.r2dbc.sqlstatement.SqlStatement;
import brain.adn.redis.ReactiveRedisComponent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;


import java.time.Duration;
import java.util.List;

@Component
public class DaoAdnPostgres implements DaoAdn {

    private final CustomRRDBCTemplate customRRDBCTemplate;
    private final TransformadorAdn transformadorAdn;
    private final ReactiveRedisComponent reactiveRedisComponent;

    private static final String CACHE_KEY = "adn:estadisticas";
    private static final String HASH_KEY = "data";
    private static final Duration CACHE_DURATION = Duration.ofMinutes(2);

    private final ObjectMapper objectMapper;

    public DaoAdnPostgres(CustomRRDBCTemplate customRRDBCTemplate, TransformadorAdn transformadorAdn, ReactiveRedisComponent reactiveRedisComponent, ObjectMapper objectMapper) {
        this.customRRDBCTemplate = customRRDBCTemplate;
        this.transformadorAdn = transformadorAdn;
        this.reactiveRedisComponent = reactiveRedisComponent;
        this.objectMapper = objectMapper;
    }

    @SqlStatement(namespace = "adn", value = "listar")
    private static String sqlListar;

    @Override
    public Flux<DtoEstadisticas> listar() {
        return reactiveRedisComponent.get(CACHE_KEY)
                .collectList()
                .flatMapMany(cachedData -> {
                    if (!cachedData.isEmpty()) {
                        return parseCachedData(cachedData.get(0));
                    } else {
                        return fetchFromDatabaseAndCache();
                    }
                });
    }

    private Flux<DtoEstadisticas> parseCachedData(Object cachedData) {
        try {
            String json = (String) cachedData;
            List<DtoEstadisticas> estadisticas = objectMapper.readValue(json, new TypeReference<List<DtoEstadisticas>>() {});
            return Flux.fromIterable(estadisticas);
        } catch (ClassCastException | JsonProcessingException e) {
            return Flux.error(new RuntimeException("Error processing cached data", e));
        }
    }

    private Flux<DtoEstadisticas> fetchFromDatabaseAndCache() {
        return customRRDBCTemplate.getDatabaseClient().sql(sqlListar)
                .map(transformadorAdn.execute()).all()
                .collectList()
                .flatMapMany(data -> {
                    cacheData(data);
                    return Flux.fromIterable(data);
                });
    }

    private void cacheData(List<DtoEstadisticas> data) {
        reactiveRedisComponent.setWithExpiration(CACHE_KEY, HASH_KEY, transformadorAdn.toCache(data), CACHE_DURATION)
                .subscribe();
    }

}


