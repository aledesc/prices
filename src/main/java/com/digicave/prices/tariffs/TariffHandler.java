package com.digicave.prices.tariffs;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;


@Component
public class TariffHandler {
    private final TariffRepository repo;

    @Autowired
    public TariffHandler(TariffRepository repo) {
        this.repo= repo;
    }

    @ApiOperation(value="Get tariffs list")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "401", description = "Authentication is required to get the requested response")
    })

    public Mono<ServerResponse> getAll(ServerRequest ignoredRequest) {
        Flux<TariffDTO> tariffs = repo.findAll().map(TariffMapper::map);
        return ServerResponse.ok().body(tariffs, Tariff.class);
    }

    @ApiOperation(value="Get tariff's details, price should be formatted showing currency symbol and code")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "401", description = "Authentication is required to get the requested response"),
            @ApiResponse(responseCode = "404", description = "Tariff not found")
    })
    public Mono<ServerResponse> get(ServerRequest request) {

        int tariffId = Integer.parseInt(request.pathVariable("id"));

        Mono<Tariff> tariff = repo.findById(tariffId);
        return tariff.flatMap(t -> ServerResponse.ok().bodyValue(t))
                .switchIfEmpty( getNotFoundServerResponse());
    }


    @ApiOperation(value="Given a date, a productet tariff and prices formatted showing currency symbol and code")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "401", description = "Authentication is required to get the requested response"),
            @ApiResponse(responseCode = "404", description = "Tariff not found")
    })
    public Mono<ServerResponse> getFromBrandProducDate(ServerRequest request) {

        int brandId = Integer.parseInt(request.pathVariable("brandId"));
        int productId = Integer.parseInt(request.pathVariable("productId"));
        LocalDate date= LocalDate.parse(request.pathVariable("date"));

        Mono<Tariff> tariff = repo.findByDateBrandProduct(brandId, productId, date);

        return tariff.flatMap(t -> ServerResponse.ok().bodyValue(t))
                .switchIfEmpty( getNotFoundServerResponse());
    }


    @ApiOperation(value="Delete a tariff")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "401", description = "Authentication is required to get the requested response")
    })
    public Mono<ServerResponse> delete(ServerRequest request) {

        Integer tariffId = Integer.valueOf( request.pathVariable("id") );

        return repo.deleteById(tariffId)
                .then(ServerResponse.ok().build())
                .switchIfEmpty(getNotFoundServerResponse());
    }

    @ApiOperation(value="Update a tariff")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "401", description = "Authentication is required to get the requested response"),
            @ApiResponse(responseCode = "404", description = "Tariff not found")
    })
    public Mono<ServerResponse> updatePrice(ServerRequest request) {

        Integer tariffId = Integer.valueOf( request.pathVariable("id") );
        Integer price= Integer.valueOf(  request.pathVariable("price") );

        Mono<Tariff> tariff= repo.findById(tariffId);

        return tariff.flatMap(t -> {

                    t.setPrice( price );
                    return repo.save( t );

                })
                .flatMap(t -> ServerResponse.ok().bodyValue(t))
                .switchIfEmpty(getNotFoundServerResponse());
    }

    @ApiOperation(value="Create a tariff")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "401", description = "Authentication is required to get the requested response")
    })
    public Mono<ServerResponse> create(ServerRequest request) {
        Mono<Tariff> tariff = request.bodyToMono(Tariff.class);
        return tariff.flatMap(repo::save)
                .flatMap(savedUser -> ServerResponse.ok().bodyValue(savedUser));
    }

    private Mono<ServerResponse> getNotFoundServerResponse() {
        Mono<String> msg= Mono.just("Tariff not found!");
        return ServerResponse.status(HttpStatus.NOT_FOUND).body(BodyInserters.fromPublisher(msg,String.class));
    }
}

