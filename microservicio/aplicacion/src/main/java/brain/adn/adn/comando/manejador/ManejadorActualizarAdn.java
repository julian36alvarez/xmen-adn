package brain.adn.adn.comando.manejador;

import brain.adn.ComandoRespuesta;
import brain.adn.manejador.ManejadorComandoRespuesta;
import brain.adn.adn.comando.ComandoAdn;
import brain.adn.adn.comando.fabrica.FabricaAdn;
import brain.adn.adn.modelo.entidad.Adn;
import brain.adn.adn.servicio.ServicioActualizarAdn;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ManejadorActualizarAdn implements ManejadorComandoRespuesta<ComandoAdn, Mono<ComandoRespuesta<Integer>>> {

    private final FabricaAdn fabricaAdn;
    private final ServicioActualizarAdn servicioActualizarAdn;

    public ManejadorActualizarAdn(FabricaAdn fabricaAdn, ServicioActualizarAdn servicioActualizarAdn) {
        this.fabricaAdn = fabricaAdn;
        this.servicioActualizarAdn = servicioActualizarAdn;
    }

    @Override
    public Mono<ComandoRespuesta<Integer>> ejecutar(ComandoAdn comandoAdn) {
        Adn adn = this.fabricaAdn.crear(comandoAdn);
        return this.servicioActualizarAdn.ejecutar(adn).map(ComandoRespuesta::new);
    }
}
