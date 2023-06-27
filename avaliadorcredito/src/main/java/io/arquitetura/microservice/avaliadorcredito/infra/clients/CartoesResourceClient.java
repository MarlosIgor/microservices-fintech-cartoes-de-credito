package io.arquitetura.microservice.avaliadorcredito.infra.clients;

import io.arquitetura.microservice.avaliadorcredito.domain.model.Cartao;
import io.arquitetura.microservice.avaliadorcredito.domain.model.CartaoCliente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "cartoes", path = "/cartoes")
public interface CartoesResourceClient {

    @GetMapping(params = "cpf")
    ResponseEntity<List<CartaoCliente>> getCartoesByClientes(@RequestParam("cpf") String cpf);

    @GetMapping(params = "renda")
    ResponseEntity<List<Cartao>> getCartoesRendaAteh(@RequestParam("renda") Long renda);
}
