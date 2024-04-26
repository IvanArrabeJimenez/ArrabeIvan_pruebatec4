package com.ivanArrabe.AgenciaTurismo;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AgenciaTurismoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgenciaTurismoApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI(){
		return new OpenAPI().info(new Info()
				.title("API Agencia de Turismo")
				.version("1.0.0")
				.description("API para gestionar las reservas de vuelos y de habitaciones de hotel."));
	}
}
