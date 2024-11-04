package brain.adn.adn.servicio;


import brain.adn.dominio.excepcion.ExcepcionDuplicidad;
import brain.adn.adn.modelo.entidad.Adn;
import brain.adn.adn.puerto.repositorio.RepositorioAdn;
import reactor.core.publisher.Mono;

public class ServicioActualizarAdn {

    private static final String EL_ADN_NO_EXISTE_EN_EL_SISTEMA = "El adn no existe en el sistema";


    private final RepositorioAdn repositorioAdn;

    public ServicioActualizarAdn(RepositorioAdn repositorioAdn) {
        this.repositorioAdn = repositorioAdn;
    }

    public Mono<Integer> ejecutar(Adn adn) {
        return this.validarExistenciaPrevia(adn)
                .flatMap(existe -> Boolean.TRUE.equals(existe) ?
                        Mono.just(adn)
                        : Mono.error(new ExcepcionDuplicidad(EL_ADN_NO_EXISTE_EN_EL_SISTEMA)))
                .flatMap(usr -> this.repositorioAdn.actualizar(adn));
    }

    private Mono<Boolean> validarExistenciaPrevia(Adn adn) {
        return this.repositorioAdn.existe(adn.getId());
    }

}
