package com.fase5.techchallenge.fiap.mscliente.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI myOpenAPI() {

        Info info = new Info()
                .title("MS-CLIENTE")
                .version("1.0.0")
                .description("Microserviço para gerenciamento de clientes");

        return new OpenAPI().info(info);
    }
}