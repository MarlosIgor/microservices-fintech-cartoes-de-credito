package io.arquitetura.microservice.cartoes.application;

import io.arquitetura.microservice.cartoes.application.dto.CartaoSaveRequest;
import io.arquitetura.microservice.cartoes.application.dto.CartaoesPorClienteResponse;
import io.arquitetura.microservice.cartoes.domain.Cartao;
import io.arquitetura.microservice.cartoes.domain.ClienteCartao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cartoes")
@RequiredArgsConstructor
public class CartoesResource {

    private final CartaoService cartaoService;
    private final ClienteCartaoService clienteCartaoService;

    @GetMapping
    public ResponseEntity<String> status() {
        return ResponseEntity.ok("ok");
    }

    @PostMapping
    public ResponseEntity<Void> cadastra(@RequestBody CartaoSaveRequest request) {
        var cartao = request.toModel();
        cartaoService.save(cartao);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(params = "renda")
    public ResponseEntity<List<Cartao>> getCartoesRendaAteh(@RequestParam("renda") Long renda) {
        List<Cartao> cartaoList = cartaoService.getCartoesRendaMenorIgual(renda);
        return ResponseEntity.ok(cartaoList);
    }

    @GetMapping(params = "cpf")
    public ResponseEntity<List<CartaoesPorClienteResponse>> getCartoesByClientes(@RequestParam("cpf") String cpf) {
        List<ClienteCartao> list = clienteCartaoService.listCartaoByCpf(cpf);
        List<CartaoesPorClienteResponse> resultList = list.stream()
                .map(CartaoesPorClienteResponse::fromModel)
                .toList();
        return ResponseEntity.ok(resultList);
    }
}
