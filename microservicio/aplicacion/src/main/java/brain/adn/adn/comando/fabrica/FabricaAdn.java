package brain.adn.adn.comando.fabrica;

import brain.adn.adn.comando.ComandoAdn;
import brain.adn.adn.modelo.entidad.Adn;
import org.springframework.stereotype.Component;


@Component
public class FabricaAdn {

    public Adn crear(ComandoAdn comandoAdn) {
        String[] dnaArray = comandoAdn.getDna().toArray(new String[0]);

        return new Adn(
                comandoAdn.getId(),
                dnaArray,
                comandoAdn.isMutant(),
                comandoAdn.getCreatedAt()
        );
    }
}