package com.m2z.tools.requirementservice.config;

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
                        "/api/requirements/all",
                        "/api/requirements").hasAuthority(ROLE_ROOT)
                .requestMatchers(
                        "/api/requirements/**").authenticated()
                .requestMatchers(
                        HttpMethod.GET,
                        "/api/requirement-priorities/**",
                        "/api/requirement-risks/**",
                        "/api/requirement-statuses/**",
                        "/api/requirement-types/**").authenticated()
                .anyRequest().hasAuthority(ROLE_ADMIN)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new CognitoAuthenticationFilter(jwkService), BasicAuthenticationFilter.class)
                .build();
    }
}
