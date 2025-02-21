package com.digicave.prices.currency;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
@Component
public class Currencies {

    public static final String EUR= "EUR";
    public static final String USD= "USD";

    private static Map<String, Currency> currencies;

    static {
        currencies = new HashMap<>();
        currencies.put("EUR", new Currency("€", EUR, 2));
        currencies.put("USD",new Currency("$", USD, 2));
    }

    public boolean exist(String currencyCode) {
        String[] keys= currencies.keySet().toArray(new String[0]);
        return Arrays.stream(keys).toList().contains( currencyCode);
    }

    public Mono<String> getFormatted(String currencyCode, Double value)
    {
        if ( exist(currencyCode) ) {

            if (value == null || value < 0)
                return Mono.empty();

            Currency _c = currencies.get(currencyCode);
            return Mono.just(_c.getFormatted(value));

        }
        else
            return Mono.empty();
    }

    public Flux<Currency> getAll() {
        return Flux.fromIterable(currencies.values().stream().toList());
    }

    public Mono<Currency> get(String currencyCode) {
        return Mono.just(currencies.get(currencyCode));
    }
}
