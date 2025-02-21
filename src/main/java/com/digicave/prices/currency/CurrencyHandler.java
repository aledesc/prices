package com.digicave.prices.currency;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.*;

@Component
public class CurrencyHandler {

    private static final String CURRENCY_NO_FOUND= "Currency not found!";
    public static final String EUR= "EUR";
    public static final String USD= "USD";

    private static final Map<String, Currency> currencies;

    static {
        currencies = new HashMap<>();
        currencies.put("EUR", new Currency("€", EUR, 2));
        currencies.put("USD",new Currency("$", USD, 2));
    }

    private boolean exist(String currencyCode) {
        String[] keys= currencies.keySet().toArray(new String[0]);
        return Arrays.stream(keys).toList().contains( currencyCode);
    }

    private String format(String currencyCode, Double value)
    {
        if ( exist(currencyCode) ) {
            if (value == null || value < 0)
                return null;

            Currency _c = currencies.get(currencyCode);
            return _c.getFormatted(value);
        }
        else
            return null;
    }


    // @formatter:off
    @ApiOperation(value="Get currencies list")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "401", description = "Authentication is required to get the requested response")
    })
    public Mono<ServerResponse> getAll(ServerRequest serverRequest) {
        return serverRequest.principal()
                .map(Principal::getName)
                .flatMap((username) ->
                    ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body( BodyInserters.fromValue(currencies.values().stream().toList()) ));
    }

    @ApiOperation(value="Get currency details")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "401", description = "Authentication is required to get the requested response"),
            @ApiResponse(responseCode = "404", description = "Currency not found")
    })
    public Mono<ServerResponse> get(ServerRequest request) {

        Optional<Currency> currency= Optional.ofNullable(currencies.get( request.pathVariable("currencyCode")  ));

        return ServerResponse.status( currency.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body( currency.isEmpty() ? BodyInserters.fromValue(CURRENCY_NO_FOUND) : BodyInserters.fromValue(currency) );
    }

    @ApiOperation(value="Get price formatted according to currency")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "401", description = "Authentication is required to get the requested response"),
            @ApiResponse(responseCode = "404", description = "Currency not found")
    })
    public Mono<ServerResponse> getFormatted(ServerRequest serverRequest) {
        return serverRequest.principal()
                .map(Principal::getName)
                .flatMap((username) -> {

                        Optional<String> str= Optional.ofNullable( format( serverRequest.pathVariable("currencyCode"), Double.valueOf(serverRequest.pathVariable("value")) ));

                        return ServerResponse.status( str.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body( str.isEmpty() ? BodyInserters.fromValue(CURRENCY_NO_FOUND) : BodyInserters.fromValue(str) );
                    }
                );
    }


    // @formatter:on

}