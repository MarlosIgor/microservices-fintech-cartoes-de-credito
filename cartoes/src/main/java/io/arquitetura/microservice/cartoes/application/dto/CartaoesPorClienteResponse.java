package io.arquitetura.microservice.cartoes.application.dto;

import io.arquitetura.microservice.cartoes.domain.ClienteCartao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartaoesPorClienteResponse {

    private String nome;
    private String bandeira;
    private BigDecimal limiteLiberado;

    public static CartaoesPorClienteResponse fromModel(ClienteCartao model) {
        return new CartaoesPorClienteResponse(
                model.getCartao().getNome(),
                model.getCartao().getBandeira().toString(),
                model.getLimite()
        );

    }
}
