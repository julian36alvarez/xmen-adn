package brain.adn.infraestructura.configuracion;

import brain.adn.infraestructura.filtro.FiltroHeaderSeguridad;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfiguracionHeader {
	
	@Bean
	public FiltroHeaderSeguridad filtroHeader() {
		return new FiltroHeaderSeguridad();
	}

}