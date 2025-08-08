package dev.pantanal.catalogo.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Catálogo de Cinema API")
                        .description("API para gerenciamento de catálogo de filmes, gêneros e pessoas")
                        .version("v1")
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT"))
                        .contact(new Contact()
                                .name("Pantanal Dev")
                                .url("https://pantanal.dev")))
                .externalDocs(new ExternalDocumentation()
                        .description("Documentação do projeto")
                        .url("https://github.com/hsborges/catalogo.pantanal.dev"));
    }
}
