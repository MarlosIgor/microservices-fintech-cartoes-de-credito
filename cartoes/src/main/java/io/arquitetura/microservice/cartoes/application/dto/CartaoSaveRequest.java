package io.arquitetura.microservice.cartoes.application.dto;

import io.arquitetura.microservice.cartoes.domain.Cartao;
import io.arquitetura.microservice.cartoes.enums.BandeiraCartao;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartaoSaveRequest {

    private String nome;
    private BandeiraCartao bandeira;
    private BigDecimal renda;
    private BigDecimal limite;

    public Cartao toModel() {
        return new Cartao(nome, bandeira, renda, limite);
    }
}
