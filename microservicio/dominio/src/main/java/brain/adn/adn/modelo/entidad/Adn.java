package brain.adn.adn.modelo.entidad;


import java.time.LocalDateTime;

import static brain.adn.dominio.ValidadorArgumento.validarLogitudMatriz;
import static brain.adn.dominio.ValidadorArgumento.validarObligatorio;

public class Adn {

    private static final String ADN_NAME_MUST_BE_PROVIDED = "Dna sequence must be provided";


    private Integer id;
    private String dna;
    private boolean isMutant;
    private LocalDateTime createdAt;

    private static final String ADN_INVALIDO = "El ADN contiene caracteres no válidos";
    private static final String DIMENSION_MATRIZ = "La matriz de ADN debe ser NxN";
    private static final int LONGITUD_SECUENCIA = 4;
    private static final int SECUENCIAS_REQUERIDAS = 2;

    public Adn(Integer id, String[] dna, boolean isMutant, LocalDateTime createdAt) {
        validarObligatorio(dna, ADN_NAME_MUST_BE_PROVIDED);
        validarLogitudMatriz(dna, DIMENSION_MATRIZ);
        validarCaracteresAdn(dna);
        this.id = id;
        this.dna = String.join(",", dna);
        this.isMutant = esMutante(dna);
        this.createdAt = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public String getDna() {
        return dna;
    }

    public boolean isMutant() {
        return isMutant;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    private boolean esMutante(String[] adn) {
        int n = adn.length;
        long contadorSecuencias =
                java.util.stream.IntStream.range(0, n)
                        .boxed()
                        .flatMap(i -> java.util.stream.IntStream.range(0, n - LONGITUD_SECUENCIA + 1)
                                .mapToObj(j -> new int[]{i, j}))
                        .filter(pos -> tieneSecuenciaHorizontal(adn, pos[0], pos[1]) || tieneSecuenciaVertical(adn, pos[1], pos[0]))
                        .count();

        if (contadorSecuencias >= SECUENCIAS_REQUERIDAS) {
            return true;
        }

        contadorSecuencias +=
                java.util.stream.IntStream.range(0, n - LONGITUD_SECUENCIA + 1)
                        .boxed()
                        .flatMap(i -> java.util.stream.IntStream.range(0, n - LONGITUD_SECUENCIA + 1)
                                .mapToObj(j -> new int[]{i, j}))
                        .filter(pos -> tieneSecuenciaDiagonal(adn, pos[0], pos[1]) || tieneSecuenciaAntiDiagonal(adn, pos[0], pos[1]))
                        .count();

        return contadorSecuencias >= SECUENCIAS_REQUERIDAS;
    }

    private boolean tieneSecuenciaHorizontal(String[] adn, int fila, int col) {
        char base = adn[fila].charAt(col);
        return java.util.stream.IntStream.range(1, LONGITUD_SECUENCIA)
                .allMatch(i -> adn[fila].charAt(col + i) == base);
    }

    private boolean tieneSecuenciaVertical(String[] adn, int fila, int col) {
        char base = adn[fila].charAt(col);
        return java.util.stream.IntStream.range(1, LONGITUD_SECUENCIA)
                .allMatch(i -> adn[fila + i].charAt(col) == base);
    }

    private boolean tieneSecuenciaDiagonal(String[] adn, int fila, int col) {
        char base = adn[fila].charAt(col);
        return java.util.stream.IntStream.range(1, LONGITUD_SECUENCIA)
                .allMatch(i -> adn[fila + i].charAt(col + i) == base);
    }

    private boolean tieneSecuenciaAntiDiagonal(String[] adn, int fila, int col) {
        char base = adn[fila].charAt(col);
        return java.util.stream.IntStream.range(1, LONGITUD_SECUENCIA)
                .allMatch(i -> adn[fila + i].charAt(col + LONGITUD_SECUENCIA - 1 - i) == base);
    }

    private void validarCaracteresAdn(String[] adn) {
        for (String secuencia : adn) {
            if (!secuencia.matches("[ATCG]+")) {
                throw new IllegalArgumentException(ADN_INVALIDO);
            }
        }
    }
}
