package brain.adn.adn.controlador;

import brain.adn.ApplicationMock;
import brain.adn.ComandoRespuesta;
import brain.adn.RedisTestConfiguration;
import brain.adn.adn.comando.ComandoAdn;
import brain.adn.adn.servicio.builder.ComandoAdnTestDataBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.http.MediaType;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = {ConsultaControladorAdn.class})
@ContextConfiguration(classes = {ApplicationMock.class, RedisTestConfiguration.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ComandoControladorAdnTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DatabaseClient databaseClient;

    @Autowired
    private R2dbcEntityTemplate r2dbcEntityTemplate;



    @Test
    @DisplayName("Deberia crear un adn")
    void deberiaCrearUnAdn() {
        // Arrange
        ComandoAdn adn = new ComandoAdnTestDataBuilder().build();

        // Act - Assert
        webTestClient.post().uri("/mutant")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(adn)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("Deberia actualizar un adn")
    void deberiaActualizarUnAdn() {
        // Arrange
        Long id = 1L;
        ComandoAdn adn = new ComandoAdnTestDataBuilder()
                .build();

        // Act - Assert
        webTestClient.put().uri("/mutant/{id}", id)
                .bodyValue(adn)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ComandoRespuesta.class)
                .consumeWith(response -> {
                    ComandoRespuesta<?> comandoRespuesta = response.getResponseBody();
                    Assertions.assertNotNull(comandoRespuesta);
                    assertThat((Integer) comandoRespuesta.getValor(), greaterThan(0));
                });
    }

    @Test
    @DisplayName("Deberia eliminar un adn")
    void deberiaEliminarUnAdn() {
        // Arrange
        Long id = 1L;
        // Act - Assert
        webTestClient.delete().uri("/mutant/{id}", id)
                .exchange()
                .expectStatus().isOk();

        Mono<Map<String, Object>> monoAdn = databaseClient.sql("SELECT * FROM adns WHERE id = " + id).fetch().first();
        StepVerifier.create(monoAdn).verifyComplete();
    }

}
