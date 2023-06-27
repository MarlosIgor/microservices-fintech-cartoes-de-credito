package io.arquitetura.microservice.avaliadorcredito.domain.model;

import lombok.Data;

@Data
public class DadosCliente {

    private Long id;
    private String nome;
    private Integer idade;
}
