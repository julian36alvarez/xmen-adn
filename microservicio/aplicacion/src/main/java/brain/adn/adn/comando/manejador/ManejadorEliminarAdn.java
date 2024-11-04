package brain.adn.adn.comando.manejador;

import brain.adn.adn.servicio.ServicioEliminarAdn;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Component
public class ManejadorEliminarAdn {

    private final ServicioEliminarAdn servicioEliminarAdn;

    public ManejadorEliminarAdn(ServicioEliminarAdn servicioEliminarAdn) {
        this.servicioEliminarAdn = servicioEliminarAdn;
    }

    public Mono<Void> ejecutar(Integer idAdn) {
        return this.servicioEliminarAdn.ejecutar(idAdn);
    }

}
