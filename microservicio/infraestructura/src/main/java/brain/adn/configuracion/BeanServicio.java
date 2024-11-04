package brain.adn.configuracion;

import brain.adn.adn.puerto.repositorio.RepositorioAdn;
import brain.adn.adn.servicio.ServicioActualizarAdn;
import brain.adn.adn.servicio.ServicioCrearAdn;
import brain.adn.adn.servicio.ServicioEliminarAdn;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanServicio {

    @Bean
    public ServicioCrearAdn servicioCrearAdn(RepositorioAdn repositorioAdn) {
        return new ServicioCrearAdn(repositorioAdn);
    }

    @Bean
    public ServicioEliminarAdn servicioEliminarAdn(RepositorioAdn repositorioAdn) {
        return new ServicioEliminarAdn(repositorioAdn);
    }

    @Bean
    public ServicioActualizarAdn servicioActualizarAdn(RepositorioAdn repositorioAdn) {
        return new ServicioActualizarAdn(repositorioAdn);
    }
}
