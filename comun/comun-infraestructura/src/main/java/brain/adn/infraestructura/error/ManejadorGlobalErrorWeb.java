package brain.adn.infraestructura.error;

import brain.adn.dominio.excepcion.*;
import brain.adn.infraestructura.excepcion.ExcepcionTecnica;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Order(-2)
@Component
public class ManejadorGlobalErrorWeb extends AbstractErrorWebExceptionHandler {

	private final Logger logger = LoggerFactory.getLogger(ManejadorGlobalErrorWeb.class);
    private static final int CODIGO_DE_RESPUESTA_POR_DEFECTO = 500;
    private static final String PACKAGE = "brain.adn";

    private static final ConcurrentHashMap<String, Integer> CODIGOS_ESTADO = new ConcurrentHashMap<>();

    public ManejadorGlobalErrorWeb(ErrorAttributes errorAttributes, ApplicationContext applicationContext,
                                   ServerCodecConfigurer serverCodecConfigurer) {
        super(errorAttributes, new WebProperties.Resources(), applicationContext);
        super.setMessageWriters(serverCodecConfigurer.getWriters());
        super.setMessageReaders(serverCodecConfigurer.getReaders());

        CODIGOS_ESTADO.put(ExcepcionLongitudValor.class.getSimpleName(), HttpStatus.BAD_REQUEST.value());
        CODIGOS_ESTADO.put(ExcepcionValorInvalido.class.getSimpleName(), HttpStatus.BAD_REQUEST.value());
        CODIGOS_ESTADO.put(ExcepcionSinDatos.class.getSimpleName(), HttpStatus.NOT_FOUND.value());
        CODIGOS_ESTADO.put(ExcepcionValorObligatorio.class.getSimpleName(), HttpStatus.BAD_REQUEST.value());
        CODIGOS_ESTADO.put(ExcepcionDuplicidad.class.getSimpleName(), HttpStatus.BAD_REQUEST.value());
        CODIGOS_ESTADO.put(ExcepcionTecnica.class.getSimpleName(), HttpStatus.INTERNAL_SERVER_ERROR.value());
		// En caso de tener otra excepcion, matricularla aca.
    }

    /**
     * Obtiene de la excepción la linea de código donde se originó la misma. En caso de no ser posible, se devulve una
     * cadena vacia.
     */
    private String obtenerOrigenDeExcepcion(Exception exception) {
        Optional<String> codeLines = (exception.getStackTrace() == null || exception.getStackTrace().length == 0)
                ? Optional.empty()
                : Arrays.stream(exception.getStackTrace()).map(StackTraceElement::toString)
                .filter(namePackage -> namePackage.contains(PACKAGE)).findFirst();
        return codeLines.orElse("");
    }

    @NonNull
    private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {

        Throwable throwable = this.getError(request);

        String excepcionNombre = ((Exception) throwable).getClass().getSimpleName();
        String mensaje = throwable.getMessage();
        Integer codigoRespuesta = CODIGOS_ESTADO.get(excepcionNombre);
        String origen = obtenerOrigenDeExcepcion((Exception) throwable);

        if (throwable instanceof ResponseStatusException) {
            codigoRespuesta = ((ResponseStatusException) throwable).getRawStatusCode();
        }

        Error error;
        if (codigoRespuesta != null) {
            error = new Error(true, excepcionNombre, mensaje, origen);
        } else {
			logger.error(excepcionNombre, throwable);
            codigoRespuesta = CODIGO_DE_RESPUESTA_POR_DEFECTO;
            error = new Error(false, excepcionNombre, mensaje, origen);
        }

        return ServerResponse.status(HttpStatus.valueOf(codigoRespuesta))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(error);
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

}
