package com.m2z.tools.diagramservice.config;

import static com.m2z.tools.security.util.SecurityUtil.*;

import com.m2z.tools.security.filters.CognitoAuthenticationFilter;
import com.m2z.tools.security.service.JwkService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterConfig(HttpSecurity http, JwkService jwkService) throws Exception {
        return http
                .cors().and()
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(
                        HttpMethod.GET,
                        "/api/diagrams/all",
                        "/api/diagrams",
                        "/api/classes/all",
                        "/api/classes",
                        "/api/classes/*",
                        "/api/properties/all",
                        "/api/properties",
                        "/api/properties/*",
                        "/api/methods/all",
                        "/api/methods",
                        "/api/methods/*",
                        "/api/parameters/all",
                        "/api/parameters",
                        "/api/parameters/*",
                        "/api/relationships/all",
                        "/api/relationships",
                        "/api/relationships/*").hasAuthority(ROLE_ROOT)
                .requestMatchers(
                        "/api/diagrams/**").authenticated()
                .requestMatchers(
                        "/api/classes/**",
                        "/api/properties/**",
                        "/api/methods/**",
                        "/api/parameters/**",
                        "/api/relationships/**").authenticated()
                .requestMatchers(
                        HttpMethod.GET,
                        "/api/class-types/**",
                        "/api/access-types/**",
                        "/api/relationship-types/**").authenticated()
                .anyRequest().hasAuthority(ROLE_ADMIN)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new CognitoAuthenticationFilter(jwkService), BasicAuthenticationFilter.class)
                .build();
    }
}
