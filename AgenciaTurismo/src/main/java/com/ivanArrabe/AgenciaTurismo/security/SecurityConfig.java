package com.ivanArrabe.AgenciaTurismo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain (HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/agency/hotels").permitAll()
                .requestMatchers("/agency/rooms").permitAll()
                .requestMatchers("/agency/room-booking/new").permitAll()
                .requestMatchers("/agency/flights/all-flights").permitAll()
                .requestMatchers("/agency/flights").permitAll()
                .requestMatchers("/agency/flight-booking/new").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .and()
                .httpBasic()
                .and()
                .build();
    }
}
