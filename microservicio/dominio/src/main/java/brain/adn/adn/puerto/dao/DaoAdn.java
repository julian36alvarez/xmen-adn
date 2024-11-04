package brain.adn.adn.puerto.dao;

import brain.adn.adn.modelo.dto.DtoEstadisticas;
import reactor.core.publisher.Flux;


public interface DaoAdn {

    /**
     * Permite listar adns
     * @return los adns
     */
    Flux<DtoEstadisticas> listar();
}
