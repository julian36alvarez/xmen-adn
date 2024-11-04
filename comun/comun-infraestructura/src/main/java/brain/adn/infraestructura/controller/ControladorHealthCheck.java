package brain.adn.infraestructura.controller;

import brain.adn.infraestructura.actuator.ManejadorHealthCheckBloques;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/healthCheck")
public class ControladorHealthCheck {

    private final ManejadorHealthCheckBloques manejadorHealthCheckBloques;

    public ControladorHealthCheck(ManejadorHealthCheckBloques manejadorHealthCheckBloques) {
        this.manejadorHealthCheckBloques = manejadorHealthCheckBloques;
    }

    @GetMapping
    public Mono<ResponseEntity<Object>> healthCheck() {
        return this.manejadorHealthCheckBloques.existenBloquesConError()
                .flatMap(error -> Mono.just(
                        Boolean.TRUE.equals(error)
                                ? new ResponseEntity<>(manejadorHealthCheckBloques.getBloquesConError(), HttpStatus.SERVICE_UNAVAILABLE)
                                : new ResponseEntity<>(true, HttpStatus.OK)
                ));
    }

}
