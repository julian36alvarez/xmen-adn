package brain.adn.infraestructura.r2dbc.sqlstatement;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Documented
public @interface SqlStatement {
       
	String value() default "";
	String namespace() default "";
}