package com.digicave.prices.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
class CurrenciesServiceTest {

        @Autowired
        private WebTestClient webTestClient;

    @Test
    public void testPublicEndpoint() {
        webTestClient.get()
                .uri("/v1/currency")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void testAnotherPublicEndpoint() {
        webTestClient.get()
                .uri("/v1/currency/USD/1")
                .exchange()
                .expectStatus().isOk();
    }


}