package brain.adn.adn.modelo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class DtoAdn {

    private Integer id;
    private List<String> dna;
    private final boolean isMutant;
    private final LocalDateTime createdAt;

}
