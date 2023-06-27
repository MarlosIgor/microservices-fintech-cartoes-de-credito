package io.arquitetura.microservice.clientes.application.dto;

import io.arquitetura.microservice.clientes.domain.Cliente;
import lombok.Data;

@Data
public class ClienteSaveRequest {

    public String cpf;
    public String nome;
    public Integer idade;


    public Cliente toModel() {
        return new Cliente(cpf, nome, idade);
    }
}
