package brain.adn;

public class ComandoRespuesta<T> {

    private T valor;

    public ComandoRespuesta() { }

    public ComandoRespuesta(T valor) {
        this.valor = valor;
    }

    public T getValor() {
        return valor;
    }
}
