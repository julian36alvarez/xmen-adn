package brain.adn.manejador;


import org.springframework.transaction.annotation.Transactional;

public interface ManejadorComando<C, M> {

    @Transactional
    M ejecutar(C comando);

}