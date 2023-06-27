package io.arquitetura.microservice.avaliadorcredito.application;

import feign.FeignException;
import io.arquitetura.microservice.avaliadorcredito.application.ex.DadosClienteNotFoundException;
import io.arquitetura.microservice.avaliadorcredito.application.ex.ErroComunicacaoMicroservicersException;
import io.arquitetura.microservice.avaliadorcredito.application.ex.ErroSolicitacaoCartaoException;
import io.arquitetura.microservice.avaliadorcredito.domain.model.*;
import io.arquitetura.microservice.avaliadorcredito.infra.clients.CartoesResourceClient;
import io.arquitetura.microservice.avaliadorcredito.infra.clients.ClienteResourceClient;
import io.arquitetura.microservice.avaliadorcredito.infra.mqueue.SolicitacaoEmissaoCartaoPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clienteResourceClient;
    private final CartoesResourceClient cartoesResourceClient;
    private final SolicitacaoEmissaoCartaoPublisher emissaoCartaoPublisher;

    public SituacaoCliente obterSituacaoCliente(String cpf)
            throws DadosClienteNotFoundException, ErroComunicacaoMicroservicersException {
        try {
            ResponseEntity<DadosCliente> clienteResponse = clienteResourceClient.dadosCliente(cpf);
            ResponseEntity<List<CartaoCliente>> cartoesResponse = cartoesResourceClient.getCartoesByClientes(cpf);
            return SituacaoCliente.builder()
                    .cliente(clienteResponse.getBody())
                    .cartoes(cartoesResponse.getBody())
                    .build();
        }
        catch (FeignException.FeignClientException e) {
            int status = e.status();
            if (HttpStatus.NOT_FOUND.value() == status) {
                throw new DadosClienteNotFoundException();
            }
            throw new ErroComunicacaoMicroservicersException(e.getMessage(), status);
        }
    }

    public RetornoAvaliacaoCliente retornoAvaliacaoCliente(String cpf, Long renda)
            throws DadosClienteNotFoundException, ErroComunicacaoMicroservicersException{
        try {
            ResponseEntity<DadosCliente> clienteResponse = clienteResourceClient.dadosCliente(cpf);
            ResponseEntity<List<Cartao>> cartoesResponse = cartoesResourceClient.getCartoesRendaAteh(renda);
            List<Cartao> cartoes = cartoesResponse.getBody();
            var listaCartoesAprovados = cartoes.stream().map(cartao -> {
                DadosCliente dadosCliente = clienteResponse.getBody();
                BigDecimal limiteBasico = cartao.getLimiteBasico();
                BigDecimal idadeDB = BigDecimal.valueOf(dadosCliente.getIdade());
                var fator = idadeDB.divide(BigDecimal.valueOf(10));
                BigDecimal limiteAprovado = fator.multiply(limiteBasico);
                CartaoAprovado aprovado = new CartaoAprovado();
                aprovado.setCartao(cartao.getNome());
                aprovado.setBandeira(cartao.getBandeira());
                aprovado.setLimiteAprovado(limiteAprovado);
                return aprovado;
            }).toList();
            return new RetornoAvaliacaoCliente(listaCartoesAprovados);
        }
        catch (FeignException.FeignClientException e) {
            int status = e.status();
            if (HttpStatus.NOT_FOUND.value() == status) {
                throw new DadosClienteNotFoundException();
            }
            throw new ErroComunicacaoMicroservicersException(e.getMessage(), status);
        }
    }

    public ProtocoloSolicitacaoCartao solicitarEmissaoCartao(DadosSolicitacaoEmissaoCartao dados) {
        try {
            emissaoCartaoPublisher.solicitarCartao(dados);
            var protocolo = UUID.randomUUID().toString();
            return new ProtocoloSolicitacaoCartao(protocolo);
        }
        catch (Exception e) {
            throw new ErroSolicitacaoCartaoException(e.getMessage());
        }
    }
}
