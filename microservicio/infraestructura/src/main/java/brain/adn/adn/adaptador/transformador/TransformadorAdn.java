package brain.adn.adn.adaptador.transformador;

import brain.adn.adn.modelo.dto.DtoEstadisticas;
import brain.adn.infraestructura.r2dbc.MapperResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.function.BiFunction;

@Component
public class TransformadorAdn implements MapperResult<DtoEstadisticas> {

    private final ObjectMapper objectMapper;

    public TransformadorAdn(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public BiFunction<Row, RowMetadata, DtoEstadisticas> execute() {
        return (row, rowMetaData) -> {
            return DtoEstadisticas.builder()
                    .count_human_dna(row.get("count_human_dna", Long.class).intValue())
                    .count_mutant_dna(row.get("count_mutant_dna", Long.class).intValue())
                    .ratio(row.get("ratio", Double.class).floatValue())
                    .build();
        };
    }


    public String toCache(List<DtoEstadisticas> data) {
        try {
            return objectMapper.writeValueAsString(data);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing data to cache", e);
        }
    }
}