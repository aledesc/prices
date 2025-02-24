package com.digicave.prices.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

//@SpringBootTest
//@AutoConfigureWebTestClient
class TariffServiceTest {

//        @Autowired
//        private WebTestClient webTestClient;
//
//        @Test
//        public void testSecureEndpointUnauthenticated() {
//            webTestClient.get()
//                    .uri("/v1/admin/tariff")
//                    .exchange()
//                    .expectStatus().isUnauthorized();
//        }
//
//        // Test secured endpoint with authentication (using @WithMockUser)
//        @Test
//        @WithMockUser // Mock a user with default username "user" and role "USER"
//        public void testSecureEndpointNoAuthenticated() {
//            webTestClient.get()
//                    .uri("/v1/tariff")
//                    .exchange()
//                    .expectStatus().isOk();
//        }
}