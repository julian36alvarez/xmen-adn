package brain.adn.infraestructura.actuator;

import brain.adn.infraestructura.excepcion.ExcepcionTecnica;
import reactor.core.publisher.Mono;

/**
 * Interface que tiene por objetivo ser implementada por todos los bloques 
 * que quieran utilizar HealthCheck.
 * Basada en el trabajo realizado por sergio.villamizar.
 *
 *
 * @author william.torres
 *
 */

public interface Salud  {

	/**
	 * Registra los bloques implementados
	 */
	void registrarBloque();
	
	/**
	 * Valida la salud del bloque
	 */
	Mono<ExcepcionTecnica> verificar();

}
