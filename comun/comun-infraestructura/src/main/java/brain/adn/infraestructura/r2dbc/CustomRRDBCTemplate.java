package brain.adn.infraestructura.r2dbc;

import brain.adn.infraestructura.excepcion.ExcepcionTecnica;
import io.r2dbc.spi.Row;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Function;

@Repository
public class CustomRRDBCTemplate {

    private final DatabaseClient databaseClient;
    private static final List<String> PARAMETROS_OBLIGATORIOS = Collections.singletonList("id");
    private static final String ERROR_OBTENIENDO_EL_NOMBRE_Y_VALOR_DE_OBJETO = "Error obteniendo el nombre y valor de objeto";

    public CustomRRDBCTemplate(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    private Map<String, List<?>> createParameters(Object object) {
        Map<String, List<?>> parameters = new HashMap<>();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                if (!Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers())) {
                    field.setAccessible(true);
                    parameters.put(field.getName(), Arrays.asList(field.get(object), field.getType()));
                    field.setAccessible(false);
                }
            } catch (Exception e) {
                throw new ExcepcionTecnica(ERROR_OBTENIENDO_EL_NOMBRE_Y_VALOR_DE_OBJETO, e);
            }
        }
        return parameters;
    }

    private DatabaseClient.GenericExecuteSpec buildSql(Object object, DatabaseClient.GenericExecuteSpec sql) {
        Map<String, List<?>> parameters = createParameters(object);
        for (Map.Entry<String, List<?>> parameter : parameters.entrySet()) {
            String parameterName = parameter.getKey();
            Object parameterValue = parameter.getValue().get(0);
            if (Objects.nonNull(parameterValue)) {
                sql = sql.bind(parameterName, parameterValue);
            } else {
                if (!PARAMETROS_OBLIGATORIOS.contains(parameterName)) {
                    sql = sql.bindNull(parameterName, (Class<?>) parameter.getValue().get(1));
                }
            }
        }
        return sql;
    }

    private DatabaseClient.GenericExecuteSpec insertParameters(Map<String, Object> parameters, DatabaseClient.GenericExecuteSpec sql) {
        for (Map.Entry<String, Object> parameter : parameters.entrySet()) {
            sql = sql.bind(parameter.getKey(), parameter.getValue());
        }
        return sql;
    }

    public Mono<Integer> create(Object object, String sql) {
        DatabaseClient.GenericExecuteSpec query = databaseClient.sql(sql)
                .filter((statement, executeFunction) -> statement.returnGeneratedValues("id").execute());
        query = buildSql(object, query);
        return query.fetch().first().map(r -> (Integer) r.get("id"));
    }

    public Mono<Integer> update(Object object, String sql) {
        DatabaseClient.GenericExecuteSpec query = databaseClient.sql(sql);
        query = buildSql(object, query);
        return query.fetch().rowsUpdated();
    }

    public Mono<Void> query(Map<String, Object> parameters, String sql) {
        DatabaseClient.GenericExecuteSpec query = databaseClient.sql(sql);
        query = insertParameters(parameters, query);
        return query.then();
    }

    public <R> Mono<R> query(Map<String, Object> parameters, Function<Row, R> mapper, String sql) {
        DatabaseClient.GenericExecuteSpec query = databaseClient.sql(sql);
        query = insertParameters(parameters, query);
        return query.map(mapper).one();
    }

    public DatabaseClient getDatabaseClient() {
        return databaseClient;
    }

}
