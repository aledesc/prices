package com.digicave.prices.tariffs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class TariffRoutingConfiguration {

	@Bean
	public RouterFunction<ServerResponse> tariffRoutes(TariffHandler handler) {
		return
			route(GET("/v1/tariff"), handler::getAll)
			.andRoute(GET("/v1/tariff/{id}"), handler::get)
			.andRoute(DELETE("/v1/tariff/{id}"), handler::delete)
			.andRoute(PATCH("/v1/tariff/{id}/{price}"), handler::updatePrice)
			.andRoute(POST("/v1/tariff"), handler::create)
			.andRoute(GET("/v1/tariff/{brandId}/{productId}/{date}"), handler::getFromBrandProducDate)
		;
	}
}