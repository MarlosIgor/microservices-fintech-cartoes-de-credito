package io.arquitetura.microservice.cartoes.infra.repository;

import io.arquitetura.microservice.cartoes.domain.ClienteCartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteCartaoRepository extends JpaRepository<ClienteCartao, Long> {

    List<ClienteCartao> findByCpf(String cpf);
}
