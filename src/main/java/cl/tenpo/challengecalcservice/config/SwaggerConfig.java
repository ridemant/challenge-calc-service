package cl.tenpo.challengecalcservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Challenge Calc API")
                        .version("1.0")
                        .description("API para cálculos con porcentaje e historial de llamadas")
                        .contact(new Contact().name("Américo Allende").email("americo.alle@gmail.com")));
    }
}