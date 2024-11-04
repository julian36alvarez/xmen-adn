package brain.adn.adn.comando;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComandoAdn implements Serializable {

    private static final long serialVersionUID = 4984490950707784374L;

    private Integer id;
    private List<String> dna;
    private boolean isMutant;
    private LocalDateTime createdAt;

}
