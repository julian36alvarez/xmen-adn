package brain.adn.infraestructura.configuracion;

import brain.adn.infraestructura.error.Error;
import com.fasterxml.classmate.TypeResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.schema.QualifiedModelName;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class ConfiguracionSwagger {

    @Value("${swagger.contact.name}")
    private String nombreContacto;

    @Value("${swagger.contact.url}")
    private String urlWebContacto;

    @Value("${swagger.contact.email}")
    private String correoContacto;

    @Value("${swagger.api.info.version}")
    private String versionApi;

    @Value("${swagger.api.info.title}")
    private String tituloApi;

    @Value("${swagger.api.info.description}")
    private String descripcionApi;

    @Value("${info.app.context}")
    private String contextoAplicacion;

    private static final Class<Error> DEFAULT_RESPONSE_OBJECT = Error.class;

    private static final ConcurrentHashMap<String, String> MENSAJES_DE_RESPUESTA = new ConcurrentHashMap<>();

    public ConfiguracionSwagger() {
        MENSAJES_DE_RESPUESTA.put("400", "El servidor no puede o no procesará la solicitud debido a algo que se percibe como un error del cliente.");
        MENSAJES_DE_RESPUESTA.put("401", "El servidor entendió la solicitud pero se niega a autorizarla.");
        MENSAJES_DE_RESPUESTA.put("403", "El acceso al recurso que intentabas alcanzar está prohibido.");
        MENSAJES_DE_RESPUESTA.put("404", "El servidor no puede encontrar el recurso solicitado.");
        MENSAJES_DE_RESPUESTA.put("500", "El servidor encontró una condición inesperada que le impidió cumplir la solicitud.");
    }

    /**
     * @return datos de contacto
     */
    private Contact getContact() {
        return new Contact(nombreContacto, urlWebContacto, correoContacto);
    }

    /**
     * @return datos de la API
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .version(versionApi)
                .title(tituloApi)
                .description(descripcionApi)
                .contact(getContact())
                .build();
    }

    private Response createResponse(String code, String description) {
        return new ResponseBuilder()
                .code(code)
                .description(description)
                .representation(MediaType.APPLICATION_JSON)
                .apply(r -> r.model(m -> m.referenceModel(ref -> ref.key(
                        k -> k.qualifiedModelName(
                                new QualifiedModelName(DEFAULT_RESPONSE_OBJECT.getPackageName(), DEFAULT_RESPONSE_OBJECT.getSimpleName())
                        ))))
                )
                .build();
    }

    @Bean
    public Docket docket() {
        Docket docket = new Docket(DocumentationType.OAS_30);
        docket.pathMapping(contextoAplicacion);
        docket.useDefaultResponseMessages(false).additionalModels(new TypeResolver().resolve(DEFAULT_RESPONSE_OBJECT));
        List<Response> responseMessages = Arrays.asList(
                createResponse("400", MENSAJES_DE_RESPUESTA.get("400")),
                createResponse("401", MENSAJES_DE_RESPUESTA.get("401")),
                createResponse("403", MENSAJES_DE_RESPUESTA.get("403")),
                createResponse("404", MENSAJES_DE_RESPUESTA.get("404")),
                createResponse("500", MENSAJES_DE_RESPUESTA.get("500"))
        );

        docket.globalResponses(HttpMethod.GET, responseMessages);
        docket.globalResponses(HttpMethod.POST, responseMessages);
        docket.globalResponses(HttpMethod.PUT, responseMessages);
        docket.globalResponses(HttpMethod.DELETE, responseMessages);

        return docket
                .select()
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .build()
                .genericModelSubstitutes(ResponseEntity.class)
                .apiInfo(apiInfo());
    }

}