package io.arquitetura.microservice.cartoes.application;

import io.arquitetura.microservice.cartoes.domain.ClienteCartao;
import io.arquitetura.microservice.cartoes.infra.repository.ClienteCartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteCartaoService {

    private final ClienteCartaoRepository repository;

    public List<ClienteCartao> listCartaoByCpf(String cpf) {
        return repository.findByCpf(cpf);
    }
}
