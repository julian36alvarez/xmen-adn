package brain.adn.infraestructura.r2dbc.sqlstatement;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;

/**
 * Procesador para la anotaciones
 */
@Component
public class DataAccessAnnotationProcessor implements BeanPostProcessor {

    /**
     * Process Before
     */
    @Override
    public Object postProcessBeforeInitialization(@NonNull Object bean, @NonNull String beanName) {
        this.configureFieldInjection(bean);
        return bean;
    }

    /**
     * Process After
     */
    @Override
    public Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName) {
        return bean;
    }

    /**
     * @param bean
     */
    private void configureFieldInjection(Object bean) {
        Class<?> managedBeanClass = bean.getClass();
        FieldCallback fieldCallback = new DataAccessFieldCallback(bean);
        ReflectionUtils.doWithFields(managedBeanClass, fieldCallback);
    }
}