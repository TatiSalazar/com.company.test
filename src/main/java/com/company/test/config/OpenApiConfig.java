package com.company.test.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(
		info = @Info(
				title = "Clients API",
				version = "1.0.0",
				description = "API para consultar y registrar clientes"))
public class OpenApiConfig {
}
