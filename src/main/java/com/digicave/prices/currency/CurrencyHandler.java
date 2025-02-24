package com.digicave.prices.currency;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

    private String format(String currencyCode, Integer value)
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

    public Mono<ServerResponse> getAll(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body( BodyInserters.fromValue(currencies.values().stream().toList()) );
    }

    public Mono<ServerResponse> get(ServerRequest request) {

        Optional<Currency> currency= Optional.ofNullable(currencies.get( request.pathVariable("currencyCode")  ));

        return currency.map(value -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body( BodyInserters.fromValue(value.toString() ))).orElseGet(() -> ServerResponse.status( HttpStatus.NOT_FOUND )
                    .contentType(MediaType.APPLICATION_JSON)
                    .body( BodyInserters.fromValue(CURRENCY_NO_FOUND)));
    }

    public Mono<ServerResponse> getFormatted(ServerRequest serverRequest) {

        Optional<String> str= Optional.ofNullable( format( serverRequest.pathVariable("currencyCode"), Integer.parseInt(serverRequest.pathVariable("value")) ));

        return str.map(s -> ServerResponse.ok()
                            .contentType(MediaType.TEXT_PLAIN)
                            .body( BodyInserters.fromValue(s) )).orElseGet(() -> ServerResponse.status( HttpStatus.NOT_FOUND )
                    .contentType(MediaType.TEXT_PLAIN)
                    .body( BodyInserters.fromValue(CURRENCY_NO_FOUND) ));
    }

    // @formatter:on

}