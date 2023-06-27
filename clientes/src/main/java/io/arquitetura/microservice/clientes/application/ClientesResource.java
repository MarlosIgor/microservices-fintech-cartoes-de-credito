package io.arquitetura.microservice.clientes.application;

import io.arquitetura.microservice.clientes.application.dto.ClienteSaveRequest;
import io.arquitetura.microservice.clientes.domain.Cliente;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("clientes")
@RequiredArgsConstructor
@Slf4j
public class ClientesResource {

    private final ClienteService service;

    @GetMapping
    public ResponseEntity<String> status() {
        log.info("Obtendo o status do microservice de clientes");
        return ResponseEntity.ok("ok");
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody ClienteSaveRequest request) {
        var cliente = request.toModel();
        service.save(cliente);
        URI headerLocation = ServletUriComponentsBuilder
                .fromCurrentRequest() // Obtém a URI base da requisição atual
                .query("cpf={cpf}") // Adiciona o parâmetro de consulta "cpf={cpf}"
                .buildAndExpand(cliente.getCpf()) // Substitui {cpf} pelo valor do CPF do cliente
                .toUri(); // Converte para URI
// Comentário: O objetivo provável deste trecho de código é construir uma URI que pode ser usada como valor do
// cabeçalho Location em uma resposta HTTP, redirecionando para algum recurso relacionado ao cliente identificado
// pelo CPF.
        return ResponseEntity.created(headerLocation).build();
    }

    @GetMapping(params = "cpf")
    public ResponseEntity<Optional<Cliente>>  dadosCliente(@RequestParam("cpf") String cpf) {
        var cliente = service.getByCPF(cpf);
        if (cliente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cliente);
    }

}