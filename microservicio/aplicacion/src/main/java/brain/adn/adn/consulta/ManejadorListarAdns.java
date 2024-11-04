package brain.adn.adn.consulta;

import brain.adn.adn.modelo.dto.DtoEstadisticas;
import brain.adn.adn.puerto.dao.DaoAdn;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class ManejadorListarAdns {

    private final DaoAdn daoAdn;

    public ManejadorListarAdns(DaoAdn daoAdn){
        this.daoAdn = daoAdn;
    }

    public Flux<DtoEstadisticas> ejecutar(){
        return this.daoAdn.listar();
    }
}
