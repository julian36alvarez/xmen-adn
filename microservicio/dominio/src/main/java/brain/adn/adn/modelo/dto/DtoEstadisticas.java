package brain.adn.adn.modelo.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DtoEstadisticas {

    private Integer count_mutant_dna;
    private Integer count_human_dna;
    private Float ratio;

    @JsonCreator
    public DtoEstadisticas(@JsonProperty("count_mutant_dna") Integer count_mutant_dna, @JsonProperty("count_human_dna") Integer count_human_dna, @JsonProperty("ratio") Float ratio) {
        this.count_mutant_dna = count_mutant_dna;
        this.count_human_dna = count_human_dna;
        this.ratio = ratio;
    }
}
