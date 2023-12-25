package com.m2z.tools.diagramservice;

import com.m2z.tools.security.config.CorsConfigurer;
import com.m2z.tools.security.model.CorsConfigProperties;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties({CorsConfigProperties.class})
@ComponentScan(
        basePackages = {
            "com.m2z.tools.diagramservice", 
            "com.m2z.tools.shared", 
            "com.m2z.tools.security"
        },
        excludeFilters = {
            @ComponentScan.Filter(
                    type = FilterType.ASSIGNABLE_TYPE,
                    value = {CorsConfigurer.class})
        })
@OpenAPIDefinition(
        info =
                @Info(
                        title = "Diagram API",
                        version = "1.0.0",
                        description = "Documentation Diagram API v1.0.0"))
public class DiagramServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DiagramServiceApplication.class, args);
    }
}
