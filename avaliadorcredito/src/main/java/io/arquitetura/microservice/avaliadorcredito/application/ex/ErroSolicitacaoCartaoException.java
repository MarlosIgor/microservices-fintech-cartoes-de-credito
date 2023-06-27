package io.arquitetura.microservice.avaliadorcredito.application.ex;

public class ErroSolicitacaoCartaoException extends RuntimeException{

    public ErroSolicitacaoCartaoException(String message) {
        super(message);
    }
}
