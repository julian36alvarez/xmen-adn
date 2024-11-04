package brain.adn.adn.servicio;

import brain.adn.adn.modelo.entidad.Adn;
import brain.adn.adn.puerto.repositorio.RepositorioAdn;
import reactor.core.publisher.Mono;


public class ServicioCrearAdn {


    private final RepositorioAdn repositorioAdn;

    public ServicioCrearAdn(RepositorioAdn repositorioAdn) {
        this.repositorioAdn = repositorioAdn;
    }

    public Mono<Boolean> ejecutar(Adn adn) {
        return Mono.just(adn)
                .flatMap(a -> this.repositorioAdn.crear(adn)
                        .thenReturn(adn.isMutant()));
    }


}