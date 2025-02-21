package com.digicave.prices.tariffs;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

public class TariffMapper {

    private static final WebClient wClient;

    static {
        wClient= WebClient.create();
    }

//    private static Mono<String> getFormatted(Double price, String currencyCode) {
//
//        String _url= "http://localhost:8080/v1/currency/" + currencyCode+ "/" + price.toString();
//
//        return wClient.get()
//                .uri(_url)
//                .retrieve()
//                .bodyToMono(String.class).map(m -> );
//    }

    public static TariffDTO map(Tariff t){

        TariffDTO dto= new TariffDTO();
        dto.setId( t.getId() );
        dto.setBrandId( t.getBrandId() );
        dto.setProductId( t.getProductId() );
        dto.setStartDate( t.getStartDate().toString() );
        dto.setEndDate( t.getStartDate().toString() );

//        String sPrice= wClient.get
        dto.setPrice( t.getPrice() + " " + t.getCurrency() );

        return dto;
    }
}
