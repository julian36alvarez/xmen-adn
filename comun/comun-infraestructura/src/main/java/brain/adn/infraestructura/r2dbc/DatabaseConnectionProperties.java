package brain.adn.infraestructura.r2dbc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConnectionProperties {

    @Value("${database.driver:h2}")
    private String driver;

    @Value("${database.name:\"\"}")
    private String databaseName;

    @Value("${database.host:\"\"}")
    private String host;

    @Value("${database.port:\"\"}")
    private String port;

    @Value("${database.username:\"\"}")
    private String username;

    @Value("${database.password:\"\"}")
    private String password;

    @Value("${database.pool.name:default-pool}")
    private String poolName;

    @Value("${database.pool.initial-size:1}")
    private int poolInitialSize;

    public String getDriver() {
        return driver;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPoolName() {
        return poolName;
    }

    public int getPoolInitialSize() {
        return poolInitialSize;
    }

}

