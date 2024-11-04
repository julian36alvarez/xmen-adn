package brain.adn.infraestructura.r2dbc;

import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;

import java.util.function.BiFunction;

@FunctionalInterface
public interface MapperResult <T> {

   BiFunction<Row, RowMetadata, T> execute();

}
