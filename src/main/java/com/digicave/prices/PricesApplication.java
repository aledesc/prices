package com.digicave.prices;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableR2dbcRepositories
@OpenAPIDefinition(info = @Info(title = "API for Tariffs, brands, products & diamonds", version = "1.0", description = "Documentation APIs v1.0"))
public class PricesApplication {
    public static void main(String[] args) {
        SpringApplication.run(PricesApplication.class, args);
    }
}

