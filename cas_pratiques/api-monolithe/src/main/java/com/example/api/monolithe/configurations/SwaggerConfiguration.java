package com.example.api.monolithe.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info =
        @Info(
            title = "API Salle de sport",
            version = "v1",
            description = "Documentation de l'API Salle de sport"),
    servers =
        @Server(
            url = "${server.servlet.context-path:/}",
            description = "URL par défaut (relative)"))
public class SwaggerConfiguration {}
