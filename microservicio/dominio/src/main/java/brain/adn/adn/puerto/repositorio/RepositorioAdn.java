package brain.adn.adn.puerto.repositorio;

import brain.adn.adn.modelo.entidad.Adn;
import reactor.core.publisher.Mono;

public interface RepositorioAdn {
    /**
     * Permite crear un adn
     *
     * @param adn
     * @return el id generado
     */
    Mono<Integer> crear(Adn adn);

    /**
     * Permite actualizar un adn
     *
     * @param adn
     */
    Mono<Integer> actualizar(Adn adn);

    /**
     * Permite eliminar un adn
     *
     * @param id
     */
    Mono<Void> eliminar(Integer id);

    /**
     * Permite validar si existe un adn con un nombre
     *
     * @param id
     * @return si existe o no
     */
    Mono<Boolean> existe(Integer id);



}
