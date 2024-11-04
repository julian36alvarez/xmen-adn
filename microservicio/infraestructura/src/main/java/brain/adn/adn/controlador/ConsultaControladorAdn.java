package brain.adn.adn.controlador;


import brain.adn.adn.consulta.ManejadorListarAdns;
import brain.adn.adn.modelo.dto.DtoAdn;
import brain.adn.adn.modelo.dto.DtoEstadisticas;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/stats")
@Api(tags={"Controlador consulta adn"})
public class ConsultaControladorAdn {

    private final ManejadorListarAdns manejadorListarAdns;

    public ConsultaControladorAdn(ManejadorListarAdns manejadorListarAdns) {
        this.manejadorListarAdns = manejadorListarAdns;
    }

    @GetMapping
    @ApiOperation("Listar Adns")
    public Flux<DtoEstadisticas> listar() {
        return this.manejadorListarAdns.ejecutar();
    }

}
