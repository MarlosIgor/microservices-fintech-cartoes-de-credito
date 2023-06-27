package io.arquitetura.microservice.cartoes.application;

import io.arquitetura.microservice.cartoes.domain.Cartao;
import io.arquitetura.microservice.cartoes.infra.repository.CartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartaoService {

    private final CartaoRepository repository;

    @Transactional
    public void save(Cartao cartao) {
        repository.save(cartao);
    }

    public List<Cartao> getCartoesRendaMenorIgual(Long renda) {
        var rendaBigDecimal = BigDecimal.valueOf(renda);
        return repository.findByRendaLessThanEqual(rendaBigDecimal);
    }

}