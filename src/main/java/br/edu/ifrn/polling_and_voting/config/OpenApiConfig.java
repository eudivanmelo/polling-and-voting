package br.edu.ifrn.polling_and_voting.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sistema de Votação e Pesquisa API")
                        .version("1.0.0")
                        .description("API REST para gerenciamento de pesquisas e votações. " +
                                "Permite criar pesquisas, adicionar opções, registrar votos e visualizar resultados.")
                        );
    }
}
