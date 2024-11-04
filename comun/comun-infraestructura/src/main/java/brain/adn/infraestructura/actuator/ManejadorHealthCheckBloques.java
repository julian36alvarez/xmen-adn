package brain.adn.infraestructura.actuator;

import brain.adn.infraestructura.error.ErrorHealthCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.EnableScheduling;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableScheduling
public class ManejadorHealthCheckBloques {

    private final Map<String, Salud> bloquesRegistrados = new HashMap<>();
    private final Map<String, ErrorHealthCheck> bloquesConError = new HashMap<> ();
    private static final Logger LOGGER = LoggerFactory.getLogger(ManejadorHealthCheckBloques.class);
    private static final String MENSAJE_ERROR_EN_BLOQUE = "Error de conexion con el bloque ";

    public void registrarme(String nombreBloque, Salud salud) {
        this.bloquesRegistrados.put(nombreBloque, salud);
    }

    @Scheduled(fixedRateString = "${health-check.time}")
    public void refrescarListadoErrores() {
        //LOGGER.info("Se está refrescando el listado de bloques registrados");
        this.bloquesRegistrados.forEach((llave, valor) -> validarBloque(llave, this.bloquesRegistrados.get(llave)));
    }

    private void validarBloque(String key, Salud salud) {
        bloquesConError.remove(key);
        salud.verificar()
                .onErrorResume(
                        throwable -> {
                            LOGGER.error(MENSAJE_ERROR_EN_BLOQUE.concat(key));
                            bloquesConError.put(key, new ErrorHealthCheck(key));
                            return Mono.empty();
                        }
                ).subscribe();
    }

    public Mono<Boolean> existenBloquesConError() {
        return Mono.just(!bloquesConError.isEmpty());
    }

    public List<ErrorHealthCheck> getBloquesConError() {
        return new ArrayList<>(bloquesConError.values());
    }

}