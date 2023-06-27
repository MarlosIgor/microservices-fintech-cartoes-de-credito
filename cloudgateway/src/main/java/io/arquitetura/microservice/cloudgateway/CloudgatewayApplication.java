package io.arquitetura.microservice.cloudgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class CloudgatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudgatewayApplication.class, args);
    }

    @Bean
    public RouteLocator locatos(RouteLocatorBuilder builder) {
        return builder
                .routes() // Inicia a configuração das rotas
                .route(r -> r.path("/clientes/**").uri("lb://clientes")) // Configura a rota para o caminho /clientes/**
                .route(r -> r.path("/cartoes/**").uri("lb://cartoes")) // Configura a rota para o caminho /cartoes/**
                .route(r -> r.path("/avaliacoes-credito/**").uri("lb://avaliadorcredito")) // Configura a rota para o caminho /avaliacoes-credito/**
                .build(); // Constrói o RouteLocator com as rotas configuradas
    }

}