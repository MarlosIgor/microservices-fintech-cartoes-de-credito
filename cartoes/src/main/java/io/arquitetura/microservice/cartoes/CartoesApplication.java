package io.arquitetura.microservice.cartoes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableRabbit
@Slf4j
public class CartoesApplication {

	public static void main(String[] args) {
		SpringApplication.run(CartoesApplication.class, args);
	}

}
