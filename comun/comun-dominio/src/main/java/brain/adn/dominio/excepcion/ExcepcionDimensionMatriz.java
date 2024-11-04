package brain.adn.dominio.excepcion;

public class ExcepcionDimensionMatriz extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public ExcepcionDimensionMatriz(String mensaje) {
        super(mensaje);
    }
}
