package brain.adn.infraestructura.r2dbc;

import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConnectionPoolFactory {

    private static final String DEFAULT_DATABASE = "h2";
    private final DatabaseConnectionProperties databaseConnectionProperties;

    public DatabaseConnectionPoolFactory(DatabaseConnectionProperties databaseConnectionProperties) {
        this.databaseConnectionProperties = databaseConnectionProperties;
    }

    private ConnectionFactory inMemory() {
        return ConnectionFactories.get("r2dbc:h2:mem:///testdb");
    }

    private ConnectionFactory external() {
        return ConnectionFactories.get(ConnectionFactoryOptions.builder()
                .option(ConnectionFactoryOptions.DRIVER, databaseConnectionProperties.getDriver())
                .option(ConnectionFactoryOptions.HOST, databaseConnectionProperties.getHost())
                .option(ConnectionFactoryOptions.PORT, Integer.parseInt(databaseConnectionProperties.getPort()))
                .option(ConnectionFactoryOptions.DATABASE, databaseConnectionProperties.getDatabaseName())
                .option(ConnectionFactoryOptions.USER, databaseConnectionProperties.getUsername())
                .option(ConnectionFactoryOptions.PASSWORD, databaseConnectionProperties.getPassword())
                .build());
    }

    private ConnectionFactory createConnection() {
        String driver = databaseConnectionProperties.getDriver();
        return DEFAULT_DATABASE.equals(driver) ? inMemory() : external();
    }

    public ConnectionFactory createConnectionPool() {
        int totalPoolSize = Runtime.getRuntime().availableProcessors() * 2 + 1;
        return new ConnectionPool(ConnectionPoolConfiguration.builder(createConnection())
                .name(databaseConnectionProperties.getPoolName())
                .initialSize(databaseConnectionProperties.getPoolInitialSize())
                .maxSize(totalPoolSize)
                .build());
    }

}

