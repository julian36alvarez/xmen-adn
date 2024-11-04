package brain.adn.infraestructura.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

public class Error implements Serializable {

    private static final long serialVersionUID = 778875022313772132L;
    private static final Logger LOGGER = LoggerFactory.getLogger(Error.class);
    protected static final String MENSAJE_DE_ERROR_POR_DEFECTO = "Ocurrió un error inesperado, favor contactar al administrador.";

    private String id;
    private String fecha;
    private String nombreExcepcion;
    private String mensaje;

    public Error() {}

    public Error(boolean registrado, String nombreExcepcion, String mensaje, String origen) {
        this.id = UUID.randomUUID().toString();
        this.fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.nombreExcepcion = nombreExcepcion;
        this.mensaje = registrado ? mensaje : MENSAJE_DE_ERROR_POR_DEFECTO;

        String mensajeError = "\n *************** Excepción detectada *************** \n";
        mensajeError = mensajeError.concat("Id: ".concat(this.id).concat("\n"));
        mensajeError = mensajeError.concat("Marca de tiempo: ".concat(this.fecha).concat("\n"));
        mensajeError = mensajeError.concat("Excepción: ".concat(this.nombreExcepcion).concat("\n"));
        mensajeError = mensajeError.concat("Origen: ".concat(origen).concat("\n"));
        mensajeError = mensajeError.concat("Mensaje: ".concat(Objects.nonNull(mensaje) ? mensaje : "").concat("\n"));

        mensajeError = mensajeError.concat("******************************************************** \n");
        LOGGER.error(mensajeError);
    }

    public String getId() {
        return id;
    }

    public String getFecha() {
        return fecha;
    }

    public String getNombreExcepcion() {
        return nombreExcepcion;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setNombreExcepcion(String nombreExcepcion) {
        this.nombreExcepcion = nombreExcepcion;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
