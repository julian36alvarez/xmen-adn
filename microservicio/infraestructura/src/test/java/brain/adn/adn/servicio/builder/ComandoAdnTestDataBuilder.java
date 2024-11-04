package brain.adn.adn.servicio.builder;

import brain.adn.adn.comando.ComandoAdn;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class ComandoAdnTestDataBuilder {

    private Integer id;
    private List<String> dna;
    private boolean isMutant;
    private LocalDateTime createdAt;

    public ComandoAdnTestDataBuilder() {
        this.dna = Arrays.asList("ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG");
        this.isMutant = false;
        this.createdAt = LocalDateTime.now();
    }

    public ComandoAdnTestDataBuilder conId(Integer id) {
        this.id = id;
        return this;
    }

    public ComandoAdnTestDataBuilder conDna(List<String> dna) {
        this.dna = dna;
        return this;
    }

    public ComandoAdnTestDataBuilder conIsMutant(boolean isMutant) {
        this.isMutant = isMutant;
        return this;
    }

    public ComandoAdnTestDataBuilder conCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public ComandoAdn build() {
        return new ComandoAdn(id, dna, isMutant, createdAt);
    }
}