package brain.adn.adn.controlador;

import brain.adn.ApplicationMock;
import brain.adn.adn.modelo.dto.DtoEstadisticas;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = {ConsultaControladorAdn.class})
@ContextConfiguration(classes = {ApplicationMock.class})
class ConsultaControladorAdnTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("Deberia obtener todos los adns registrados")
    void deberiaObtenerTodosLosAdnsRegistrados() {
        // Arrange
        // Act - Assert
        webTestClient.get().uri("/stats")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(DtoEstadisticas.class)
                .consumeWith(clientResponse -> {
                    if (clientResponse.getStatus().is5xxServerError()) {
                        System.out.println("Error 500: " + clientResponse);
                    } else {
                        List<DtoEstadisticas> dtoEstadisticas = clientResponse.getResponseBody();
                        assertThat(dtoEstadisticas, hasSize(1));
                        DtoEstadisticas estadisticas = dtoEstadisticas.get(0);
                        assertThat(estadisticas.getCount_human_dna(), is(notNullValue()));
                        assertThat(estadisticas.getCount_mutant_dna(), is(notNullValue()));
                        assertThat(estadisticas.getRatio(), is(notNullValue()));
                    }
                });
    }

}