package brain.adn.adn.servicio;

import brain.adn.adn.modelo.entidad.Adn;
import brain.adn.adn.puerto.repositorio.RepositorioAdn;
import brain.adn.adn.servicio.builder.AdnTestDataBuilder;
import brain.adn.core.BasePrueba;
import brain.adn.dominio.excepcion.ExcepcionDimensionMatriz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServicioCrearAdnTest {

    private RepositorioAdn repositorioAdn;
    private ServicioCrearAdn servicioCrearAdn;

    @BeforeEach
    void setUp() {
        repositorioAdn = mock(RepositorioAdn.class);
        servicioCrearAdn = new ServicioCrearAdn(repositorioAdn);
    }

    @Test
    @DisplayName("Deberia crear un adn y verificar si es mutante")
    void deberiaCrearUnAdnYVerificarSiEsMutante() {
        Adn adn = new AdnTestDataBuilder().conDna(AdnTestConstants.DNA_MUTANT_ROW).conIsMutant(true).build();
        when(repositorioAdn.crear(Mockito.any(Adn.class))).thenReturn(Mono.just(1));
        Mono<Boolean> esMutante = servicioCrearAdn.ejecutar(adn);
        StepVerifier.create(esMutante)
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    @DisplayName("Deberia crear un adn y verificar si no es mutante")
    void deberiaCrearUnAdnYVerificarSiNoEsMutante() {
        Adn adn = new AdnTestDataBuilder().conDna(AdnTestConstants.DNA_NOT_MUTANT_1).conIsMutant(false).build();
        when(repositorioAdn.crear(Mockito.any(Adn.class))).thenReturn(Mono.just(1));
        Mono<Boolean> esMutante = servicioCrearAdn.ejecutar(adn);
        StepVerifier.create(esMutante)
                .expectNext(false)
                .verifyComplete();
    }

    @Test
    @DisplayName("Deberia lanzar excepcion si el ADN contiene caracteres no válidos")
    void deberiaLanzarExcepcionSiAdnContieneCaracteresNoValidos() {
        AdnTestDataBuilder builder = new AdnTestDataBuilder().conDna(AdnTestConstants.DNA_NOT_VALID);
        BasePrueba.assertThrows(() -> builder.build(),
                IllegalArgumentException.class,
                "El ADN contiene caracteres no válidos");
    }

    @Test
    @DisplayName("Deberia lanzar excepcion si la matriz de ADN no es NxN")
    void deberiaLanzarExcepcionSiMatrizAdnNoEsNxN() {
        AdnTestDataBuilder builder = new AdnTestDataBuilder().conDna(AdnTestConstants.DNA_MUTANT_COLUMN_ERROR);
        BasePrueba.assertThrows(() -> builder.build(),
                ExcepcionDimensionMatriz.class,
                "La matriz de ADN debe ser NxN");
    }

    @Test
    @DisplayName("Deberia retornar true si el ADN tiene suficientes secuencias mutantes")
    void deberiaRetornarTrueSiAdnTieneSuficientesSecuenciasMutantes() {
        Adn adn = new AdnTestDataBuilder().conDna(AdnTestConstants.DNA_MUTANT_ROW_3).conIsMutant(true).build();
        when(repositorioAdn.crear(Mockito.any(Adn.class))).thenReturn(Mono.just(1));
        Mono<Boolean> esMutante = servicioCrearAdn.ejecutar(adn);
        StepVerifier.create(esMutante)
                .expectNext(true)
                .verifyComplete();
    }
}