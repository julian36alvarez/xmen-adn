package brain.adn.infraestructura.r2dbc;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.lang.NonNull;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.reactive.TransactionalOperator;

@Configuration
@EnableR2dbcRepositories
@EnableTransactionManagement(proxyTargetClass = true)
public class DatabaseConnectionConfiguration extends AbstractR2dbcConfiguration {

    @Value("${database.initialization.schema}")
    private String schemaSQL;

    @Value("${database.initialization.data}")
    private String dataSQL;

    private final DatabaseConnectionPoolFactory databaseConnectionPoolFactory;

    public DatabaseConnectionConfiguration(DatabaseConnectionPoolFactory databaseConnectionPoolFactory) {
        this.databaseConnectionPoolFactory = databaseConnectionPoolFactory;
    }

    @Bean
    @NonNull
    @Override
    public ConnectionFactory connectionFactory() {
        return databaseConnectionPoolFactory.createConnectionPool();
    }

    @Bean
    public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {

        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        CompositeDatabasePopulator compositeDatabasePopulator = new CompositeDatabasePopulator();
        compositeDatabasePopulator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource(schemaSQL)));
        compositeDatabasePopulator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource(dataSQL)));
        initializer.setDatabasePopulator(compositeDatabasePopulator);

        return initializer;
    }

    @Bean
    ReactiveTransactionManager transactionManager(ConnectionFactory connectionFactory) {
        return new R2dbcTransactionManager(connectionFactory);
    }

    @Bean
    TransactionalOperator transactionalOperator(ReactiveTransactionManager transactionManager) {
        return TransactionalOperator.create(transactionManager);
    }

}

