package brain.adn.actuator;

import brain.adn.infraestructura.actuator.DatabaseHealthIndicator;
import brain.adn.infraestructura.actuator.ManejadorHealthCheckBloques;
import brain.adn.infraestructura.actuator.Salud;
import brain.adn.infraestructura.excepcion.ExcepcionTecnica;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Component
public class SaludPostgresql implements Salud {

    private final ConnectionFactory connectionFactory;
    private static final String CONSULTA_PRUEBA = "SELECT 1";
    private static final String BLOQUE_DOWN = "El bloque se encuentra DOWN";
    private final ManejadorHealthCheckBloques manejadorHealthCheckBloques;
    private static final String HEALTH_CHECK_R2DBC = "HealthCheckPostgresql";

    public SaludPostgresql(ConnectionFactory connectionFactory, ManejadorHealthCheckBloques manejadorHealthCheckBloques) {
        this.connectionFactory = connectionFactory;
        this.manejadorHealthCheckBloques = manejadorHealthCheckBloques;
        this.registrarBloque();
    }

    @Override
    public void registrarBloque() {
        this.manejadorHealthCheckBloques.registrarme(HEALTH_CHECK_R2DBC, this);
    }

    @Override
    public Mono<ExcepcionTecnica> verificar() {
        DatabaseHealthIndicator databaseHealthIndicator = new DatabaseHealthIndicator(connectionFactory, CONSULTA_PRUEBA);
        return databaseHealthIndicator.check().flatMap(
                health -> !health.getStatus().equals(Status.UP)
                        ? Mono.error(new ExcepcionTecnica(BLOQUE_DOWN))
                        : Mono.empty()
        );
    }

}
