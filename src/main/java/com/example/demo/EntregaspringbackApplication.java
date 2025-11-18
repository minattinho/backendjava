package com.example.demo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "Entrega Spring Back", version = "1.0", description = "API para entrega de atividades Spring"))
@SpringBootApplication
public class EntregaspringbackApplication {
    public static void main(String[] args) {
        SpringApplication.run(EntregaspringbackApplication.class, args);
    }
}
