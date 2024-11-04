package brain.adn.adn.servicio;


import brain.adn.adn.puerto.repositorio.RepositorioAdn;
import reactor.core.publisher.Mono;

public class ServicioEliminarAdn {

    private final RepositorioAdn repositorioAdn;

    public ServicioEliminarAdn(RepositorioAdn repositorioAdn) {
        this.repositorioAdn = repositorioAdn;
    }

    public Mono<Void> ejecutar(Integer id) {
        return this.repositorioAdn.eliminar(id);
    }
}
