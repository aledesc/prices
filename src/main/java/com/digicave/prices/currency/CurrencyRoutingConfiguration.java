package com.digicave.prices.currency;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;


@Configuration
public class CurrencyRoutingConfiguration {
	@Bean
	@RouterOperations(
			{
					@RouterOperation(path = "/v1/currency"
						,produces = {
						MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET, beanClass = CurrencyHandler.class, beanMethod = "getAll",
						operation = @Operation(operationId = "getAll"
								, responses = {
								@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = Currency.class))),
								}
						)),
					@RouterOperation(path = "/v1/currency/{currencyCode}"
							, produces = {
							MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET, beanClass = CurrencyHandler.class, beanMethod = "get",
							operation = @Operation(operationId = "get"
									, responses = {
									@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = Currency.class))),
									@ApiResponse(responseCode = "404", description = "Currency not found!")}
									,parameters = {
									@Parameter(in = ParameterIn.PATH, name = "currencyCode")
							}
							)),
					@RouterOperation(path = "/v1/currency/{currencyCode}/{value}"
							, produces = {
							MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET, beanClass = CurrencyHandler.class, beanMethod = "getFormatted",
							operation = @Operation(operationId = "getFormatted"
									, responses = {
									@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = Currency.class))),
									@ApiResponse(responseCode = "404", description = "Currency not found!")}
									,parameters = {
									@Parameter(in = ParameterIn.PATH, name = "currencyCode"),
									@Parameter(in = ParameterIn.PATH, name = "value")
							}
							)),
			})
	public RouterFunction<ServerResponse> currencyRoutes(CurrencyHandler handler) {
		return
			route(GET("/v1/currency"), handler::getAll)
			.andRoute(GET("/v1/currency/{currencyCode}"), handler::get)
			.andRoute(GET("/v1/currency/{currencyCode}/{value}"), handler::getFormatted)
		;
	}
}