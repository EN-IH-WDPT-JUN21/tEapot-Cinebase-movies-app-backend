//package com.ironhack.apigateway.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@EnableWebFluxSecurity
//public class SecurityConfig {
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//
//    @Bean
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
//        return http.authorizeExchange()
//                .pathMatchers(HttpMethod.GET,"/**").permitAll()
//                .anyExchange().authenticated()
//                .and()
//                .csrf().disable()
//                .httpBasic()
//                .and()
//                .build();
//    }
//
//    @Bean
//    public MapReactiveUserDetailsService userDetailsService() {
//
//        UserDetails admin = User
//                .withUsername("admin")
//                .password(passwordEncoder.encode("admin"))
//                .roles("ADMIN")
//                .build();
//
//        List<UserDetails> userDetailsList = new ArrayList<>();
//        userDetailsList.add(admin);
//
//        return new MapReactiveUserDetailsService(userDetailsList);
//    }
//
//
//}
