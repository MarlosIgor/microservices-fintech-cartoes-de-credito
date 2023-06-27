package io.arquitetura.microservice.clientes.application;

import io.arquitetura.microservice.clientes.domain.Cliente;
import io.arquitetura.microservice.clientes.infra.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    @Transactional
    public void save(Cliente cliente) {
        if (repository.existsByCpf(cliente.getCpf())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Já existe um cliente com esse CPF");
        }
        repository.save(cliente);
    }

    public Optional<Cliente> getByCPF(String cpf) {
        return repository.findByCpf(cpf);
    }
}
