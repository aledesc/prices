package com.digicave.prices;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {

    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password(".")
                .roles("USER") // Assigns role "ROLE_USER"
                .build();

        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("..")
                .roles("ADMIN") // Assigns role "ROLE_ADMIN"
                .build();

        return new MapReactiveUserDetailsService(user); //, admin);
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
            .authorizeExchange(exchanges -> exchanges

                .pathMatchers(
                        "/swagger-ui.html",
                        "/swagger-ui/**",       // Swagger UI resources
                        "/v3/api-docs/**",      // OpenAPI JSON docs
                        "/webjars/**",         // WebJars for Swagger UI
                        "/swagger-resources/**", // Swagger resources
                        "/v1/currency/**",
                        "/v1/tariff/**").permitAll()


                .pathMatchers("/v1/admin/**").authenticated()

                .pathMatchers("/v1/admin/**").hasRole("ADMIN")
                .pathMatchers(
                        "/v1/tariff/**",
                        "/v1/currency/**").hasAnyRole("USER", "ADMIN")

            )
//                .formLogin(formLogin -> formLogin.disable()) // Disable form login (for REST APIs)
//                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable) // Optional: Disable HTTP Basic
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .build();
    }








}
