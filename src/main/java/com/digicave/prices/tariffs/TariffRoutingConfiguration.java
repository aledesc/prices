package com.digicave.prices.tariffs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class TariffRoutingConfiguration {

	@Bean
	@RouterOperations(
			{
					@RouterOperation(path = "/v1/tariff"
							, produces = {
							MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET, beanClass = TariffHandler.class, beanMethod = "getAll",
							operation = @Operation(operationId = "getAll"
									, responses = {
									@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = Tariff.class))),
									@ApiResponse(responseCode = "401", description = "Authentication is required to get the requested response.")}
									)),
					@RouterOperation(path = "/v1/tariff/{id}"
							, produces = {
							MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET, beanClass = TariffHandler.class, beanMethod = "get",
							operation = @Operation(operationId = "get"
									, responses = {
									@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Tariff.class))),
									@ApiResponse(responseCode = "404", description = "Tariff no found!"),
									@ApiResponse(responseCode = "401", description = "Authentication is required to get the requested response.")}
									,parameters = {
											@Parameter(in = ParameterIn.PATH, name = "id")
									}
							)),
					@RouterOperation(path = "/v1/tariff/{id}"
							, produces = {
							MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.DELETE, beanClass = TariffHandler.class, beanMethod = "delete",
							operation = @Operation(operationId = "delete"
									, responses = {
									@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Tariff.class))),
									@ApiResponse(responseCode = "404", description = "Tariff no found!"),
									@ApiResponse(responseCode = "401", description = "Authentication is required to get the requested response.")}
									,parameters = {
											@Parameter(in = ParameterIn.PATH, name = "id")
									}
							)),
					@RouterOperation(path = "/v1/tariff/{id}/{price}"
							, produces = {
							MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.PATCH, beanClass = TariffHandler.class, beanMethod = "updatePrice",
							operation = @Operation(operationId = "updatePrice"
									, responses = {
									@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Tariff.class))),
									@ApiResponse(responseCode = "404", description = "Tariff no found!"),
									@ApiResponse(responseCode = "401", description = "Authentication is required to get the requested response.")}
									,parameters = {
											@Parameter(in = ParameterIn.PATH, name = "id"),
											@Parameter(in = ParameterIn.PATH, name = "price"),
									}
							)),
					@RouterOperation(path = "/v1/tariff"
							, produces = {
							MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST, beanClass = TariffHandler.class, beanMethod = "create",
							operation = @Operation(operationId = "create"
									, responses = {
									@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Tariff.class))),
									@ApiResponse(responseCode = "401", description = "Authentication is required to get the requested response.")}
									, requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = Tariff.class)))
							)),
					@RouterOperation(path = "/v1/tariff{brandId}/{productId}/{date}"
							, produces = {
							MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST, beanClass = TariffHandler.class, beanMethod = "getFromBrandProductDate",
							operation = @Operation(operationId = "getFromBrandProductDate"
									, responses = {
									@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Tariff.class))),
									@ApiResponse(responseCode = "401", description = "Authentication is required to get the requested response.")}
									,parameters = {
										@Parameter(in = ParameterIn.PATH, name = "brandId"),
										@Parameter(in = ParameterIn.PATH, name = "productId"),
										@Parameter(in = ParameterIn.PATH, name = "date")
									}
							)),

			})

	public RouterFunction<ServerResponse> tariffRoutes(TariffHandler handler) {
		return
			route(GET("/v1/tariff"), handler::getAll)
			.andRoute(GET("/v1/tariff/{id}"), handler::get)
			.andRoute(DELETE("/v1/tariff/{id}"), handler::delete)
			.andRoute(PATCH("/v1/tariff/{id}/{price}"), handler::updatePrice)
			.andRoute(POST("/v1/tariff"), handler::create)
			.andRoute(GET("/v1/tariff/{brandId}/{productId}/{date}"), handler::getFromBrandProductDate)
		;
	}
}