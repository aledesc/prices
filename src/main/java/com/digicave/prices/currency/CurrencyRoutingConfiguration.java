package com.digicave.prices.currency;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;


@Configuration
public class CurrencyRoutingConfiguration {
	@Bean
	public RouterFunction<ServerResponse> currencyRoutes(CurrencyHandler handler) {
		return
			route(GET("/v1/currency"), handler::getAll)
			.andRoute(GET("/v1/currency/{currencyCode}"), handler::get)
			.andRoute(GET("/v1/currency/{currencyCode}/{value}"), handler::getFormatted)
		;
	}
}