package io.arquitetura.microservice.avaliadorcredito.infra.clients;

import io.arquitetura.microservice.avaliadorcredito.domain.model.DadosCliente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//para acessar a url pelo cliente http direto sem o Gateway
//@FeignClient(url = "http://localhost:8080", path = "/clientes")

//acessando pelo Gateway com balanceamento
@FeignClient(value = "clientes", path = "/clientes")
public interface ClienteResourceClient {

    @GetMapping(params = "cpf")
    ResponseEntity<DadosCliente> dadosCliente(@RequestParam("cpf") String cpf);
}
