package com.example.devices.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // ADMIN: Are acces full la CRUD pe dispozitive și la mapări
                        .requestMatchers(HttpMethod.POST, "/devices/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/devices/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/devices/**").hasAuthority("ADMIN")
                        .requestMatchers("/linked_user/**").hasAuthority("ADMIN") // Doar adminul asociază useri

                        // ADMIN: Poate vedea toate dispozitivele
                        .requestMatchers(HttpMethod.GET, "/devices").hasAuthority("ADMIN")

                        // USER & ADMIN: Pot vedea dispozitivele specifice unui user (Clientul își vede propriile device-uri)
                        // Aici poți rafina accesul, dar deocamdată authenticated() e suficient dacă Gateway-ul filtrează
                        .requestMatchers(HttpMethod.GET, "/devices/user/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/devices/{id}").authenticated()

                        // Orice altceva trebuie să fie autentificat
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}