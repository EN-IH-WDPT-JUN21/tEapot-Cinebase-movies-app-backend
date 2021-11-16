package com.ironhack.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;

@Configuration
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ApiGatewayConfiguration {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p.path("/api/playlist/**")
                        .uri("lb://PLAYLIST-SERVICE"))
                .route(p -> p.path("/api/playlist**")
                        .uri("lb://PLAYLIST-SERVICE"))
                .route(p -> p.path("/api/users/**")
                        .uri("lb://PLAYLIST-SERVICE"))
                .route(p -> p.path("/api/users**")
                        .uri("lb://PLAYLIST-SERVICE"))
                .build();
    }
}
