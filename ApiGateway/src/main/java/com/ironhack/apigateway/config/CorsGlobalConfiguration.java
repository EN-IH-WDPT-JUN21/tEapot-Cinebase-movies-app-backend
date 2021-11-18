//package com.ironhack.apigateway.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.reactive.config.CorsRegistry;
//import org.springframework.web.reactive.config.EnableWebFlux;
//import org.springframework.web.reactive.config.WebFluxConfigurer;
//
//import java.util.Arrays;
//
//@Configuration
//@EnableWebFlux
//public class CorsGlobalConfiguration implements WebFluxConfigurer {
//
//    @Override
//    public void addCorsMappings(CorsRegistry corsRegistry) {
//        corsRegistry.addMapping("/**")
//                .allowedOrigins("http://localhost:4200")
//                .allowedMethods("PUT", "GET", "POST", "HEAD", "OPTION")
//                .maxAge(3600);
//    }
//}
