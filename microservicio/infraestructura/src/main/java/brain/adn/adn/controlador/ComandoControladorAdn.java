package brain.adn.adn.controlador;

import brain.adn.ComandoRespuesta;
import brain.adn.adn.comando.ComandoAdn;
import brain.adn.adn.comando.manejador.ManejadorActualizarAdn;
import brain.adn.adn.comando.manejador.ManejadorCrearAdn;
import brain.adn.adn.comando.manejador.ManejadorEliminarAdn;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/mutant")
@Api(tags = {"Controlador comando adn"})
public class ComandoControladorAdn {

    private final ManejadorCrearAdn manejadorCrearAdn;
    private final ManejadorEliminarAdn manejadorEliminarAdn;
    private final ManejadorActualizarAdn manejadorActualizarAdn;

    public ComandoControladorAdn(ManejadorCrearAdn manejadorCrearAdn,
                                 ManejadorEliminarAdn manejadorEliminarAdn,
                                 ManejadorActualizarAdn manejadorActualizarAdn) {
        this.manejadorCrearAdn = manejadorCrearAdn;
        this.manejadorEliminarAdn = manejadorEliminarAdn;
        this.manejadorActualizarAdn = manejadorActualizarAdn;
    }

    @PostMapping
    @ApiOperation("Crear Adn")
    public Mono<ResponseEntity<Void>> crear(@RequestBody ComandoAdn comandoAdn) {
        return manejadorCrearAdn.ejecutar(comandoAdn)
                .map(respuesta -> ResponseEntity.status(respuesta.getValor() == 1 ? HttpStatus.OK : HttpStatus.FORBIDDEN).<Void>build());
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation("Eliminar Adn")
    public Mono<Void> eliminar(@PathVariable @ApiParam("Id del adn") Integer id) {
        return manejadorEliminarAdn.ejecutar(id);
    }

    @PutMapping(value = "/{id}")
    @ApiOperation("Actualizar Adn")
    public Mono<ComandoRespuesta<Integer>> actualizar(@RequestBody ComandoAdn comandoAdn, @PathVariable Integer id) {
        comandoAdn.setId(id);
        return manejadorActualizarAdn.ejecutar(comandoAdn);
    }

}