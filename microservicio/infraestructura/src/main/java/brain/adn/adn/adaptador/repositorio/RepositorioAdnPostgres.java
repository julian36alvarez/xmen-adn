package brain.adn.adn.adaptador.repositorio;

import brain.adn.infraestructura.r2dbc.CustomRRDBCTemplate;
import brain.adn.infraestructura.r2dbc.sqlstatement.SqlStatement;
import brain.adn.adn.modelo.entidad.Adn;
import brain.adn.adn.puerto.repositorio.RepositorioAdn;
import io.r2dbc.spi.Row;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Repository
class RepositorioAdnPostgres implements RepositorioAdn {

    private final CustomRRDBCTemplate customRRDBCTemplate;

    @SqlStatement(namespace = "adn", value = "crear")
    private static String sqlCrear;

    @SqlStatement(namespace = "adn", value = "actualizar")
    private static String sqlActualizar;

    @SqlStatement(namespace = "adn", value = "eliminar")
    private static String sqlEliminar;

    @SqlStatement(namespace = "adn", value = "existe")
    private static String sqlExiste;

    @SqlStatement(namespace = "adn", value = "existeExcluyendoId")
    private static String sqlExisteExcluyendoId;

    public RepositorioAdnPostgres(CustomRRDBCTemplate customRRDBCTemplate) {
        this.customRRDBCTemplate = customRRDBCTemplate;
    }

    @Override
    public Mono<Integer> crear(Adn adn) {
        return this.customRRDBCTemplate.create(adn, sqlCrear);
    }

    @Override
    public Mono<Void> eliminar(Integer id) {
        Map<String, Object> paramSource = new HashMap<>();
        paramSource.put("id", id);
        return this.customRRDBCTemplate.query(paramSource, sqlEliminar);
    }

    @Override
    public Mono<Boolean> existe(Integer id) {
        Map<String, Object> paramSource = new HashMap<>();
        paramSource.put("id", id);

        Function<Row, Boolean> mapper = row -> {
            Long value = row.get("CNT", Long.class);
            return Objects.nonNull(value) && value > 0;
        };

        return this.customRRDBCTemplate.query(paramSource, mapper, sqlExiste);
    }

    @Override
    public Mono<Integer> actualizar(Adn adn) {
        return this.customRRDBCTemplate.update(adn, sqlActualizar);
    }



}
