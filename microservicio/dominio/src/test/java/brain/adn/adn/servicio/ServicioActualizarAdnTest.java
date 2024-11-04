package brain.adn.adn.servicio;

import brain.adn.core.BasePrueba;
import brain.adn.dominio.excepcion.ExcepcionDuplicidad;
import brain.adn.adn.modelo.entidad.Adn;
import brain.adn.adn.puerto.repositorio.RepositorioAdn;
import brain.adn.adn.servicio.builder.AdnTestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServicioActualizarAdnTest {

    private static final Integer CANTIDAD_DE_REGISTROS_AFECTADOS = 1;

    private RepositorioAdn repositorioAdn;
    private ServicioActualizarAdn servicioActualizarAdn;

    @BeforeEach
    void setUp(){
        repositorioAdn = mock(RepositorioAdn.class);
        servicioActualizarAdn = new ServicioActualizarAdn(repositorioAdn);
    }



}
