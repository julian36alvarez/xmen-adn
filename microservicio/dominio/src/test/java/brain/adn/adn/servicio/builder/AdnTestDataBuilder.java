package brain.adn.adn.servicio.builder;

import brain.adn.adn.modelo.entidad.Adn;

import java.time.LocalDateTime;

public class AdnTestDataBuilder {

    private Integer id;
    private String[] dna;
    private boolean isMutant;
    private LocalDateTime createdAt;

    public AdnTestDataBuilder() {
        this.dna = new String[]{"ATCG"};
        this.isMutant = false;
        this.createdAt = LocalDateTime.now();
    }

    public AdnTestDataBuilder conId(Integer id) {
        this.id = id;
        return this;
    }

    public AdnTestDataBuilder conDna(String[] dna) {
        this.dna = dna;
        return this;
    }

    public AdnTestDataBuilder conIsMutant(boolean isMutant) {
        this.isMutant = isMutant;
        return this;
    }

    public AdnTestDataBuilder conCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Adn build() {
        return new Adn(id, dna, isMutant, createdAt);
    }
}