package brain.adn.dominio;

import brain.adn.dominio.excepcion.ExcepcionDimensionMatriz;
import brain.adn.dominio.excepcion.ExcepcionValorObligatorio;

public class ValidadorArgumento {

    private ValidadorArgumento() {
    }

    public static void validarObligatorio(Object valor, String mensaje) {
        if (valor == null) {
            throw new ExcepcionValorObligatorio(mensaje);
        }
    }

    public static void validarLogitudMatriz(String[] dna, String mensaje) {
        if (dna == null || dna.length == 0) {
            throw new ExcepcionDimensionMatriz(mensaje);
        }
        int longitudFila = dna[0].length();
        for (String fila : dna) {
            if (fila.length() != longitudFila) {
                throw new ExcepcionDimensionMatriz(mensaje);
            }
        }
    }


}
