package brain.adn.adn.comando.manejador;

import brain.adn.ComandoRespuesta;
import brain.adn.adn.comando.ComandoAdn;
import brain.adn.adn.comando.fabrica.FabricaAdn;
import brain.adn.adn.modelo.entidad.Adn;
import brain.adn.adn.servicio.ServicioCrearAdn;
import brain.adn.manejador.ManejadorComandoRespuesta;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ManejadorCrearAdn implements ManejadorComandoRespuesta<ComandoAdn, Mono<ComandoRespuesta<Integer>>> {

    private final FabricaAdn fabricaAdn;
    private final ServicioCrearAdn servicioCrearAdn;

    public ManejadorCrearAdn(FabricaAdn fabricaAdn, ServicioCrearAdn servicioCrearAdn) {
        this.fabricaAdn = fabricaAdn;
        this.servicioCrearAdn = servicioCrearAdn;
    }

    @Override
    public Mono<ComandoRespuesta<Integer>> ejecutar(ComandoAdn comandoAdn) {
        Adn adn = this.fabricaAdn.crear(comandoAdn);
        return this.servicioCrearAdn.ejecutar(adn)
                .map(isMutant -> new ComandoRespuesta<>(isMutant ? 1 : 0));
    }
}
