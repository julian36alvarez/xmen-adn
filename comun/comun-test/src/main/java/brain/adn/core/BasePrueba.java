package brain.adn.core;

import reactor.core.CorePublisher;
import reactor.test.StepVerifier;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

import static java.util.Objects.isNull;


public class BasePrueba {

    private static final String SE_ESPERABA_LA_EXCEPCION = "Se esperaba la excepcion ";
    private static final String CON_EL_MENSAJE = " con el mensaje: ";
    private static final String PERO_OCURRIO = ". Pero ocurrió: ";

    public static <T> void assertThrows(Supplier<T> supplier, Class<? extends Exception> expectedException, String expectedMessage) {
        try {
            supplier.get();
        } catch (Exception exception) {
            String actualMessage = exception.getMessage();
            boolean status = expectedException.isInstance(exception) && actualMessage.contains(expectedMessage);
            if (!status) {
                throw new AssertionError(SE_ESPERABA_LA_EXCEPCION.concat(expectedException.getCanonicalName())
                        .concat(CON_EL_MENSAJE).concat(expectedMessage)
                        .concat(PERO_OCURRIO)
                        .concat(exception.toString())
                );
            }
        }
    }

    public static <T> void assertReactiveThrows(ReactiveStreamAction<T> action, final Class<? extends Exception> expectedException, String expectedMessage) {
        AtomicReference<Throwable> throwable = new AtomicReference<>();
        try {
            StepVerifier.create(action.execute())
                    .expectErrorMatches(error -> {
                                throwable.set(error);
                                return expectedException.isInstance(error) && error.getMessage().equals(expectedMessage);
                            }
                    ).verify();
        } catch (AssertionError assertionError) {
            if (isNull(throwable.get())) {
                throw assertionError;
            }
            throw new AssertionError(SE_ESPERABA_LA_EXCEPCION.concat(expectedException.getCanonicalName())
                    .concat(CON_EL_MENSAJE).concat(expectedMessage)
                    .concat(PERO_OCURRIO)
                    .concat(throwable.get().toString()));
        }
    }

    @FunctionalInterface
    public interface ReactiveStreamAction<T> {
        CorePublisher<T> execute();
    }

}
