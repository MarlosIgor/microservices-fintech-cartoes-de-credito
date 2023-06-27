package io.arquitetura.microservice.avaliadorcredito.application.ex;

import lombok.Getter;

public class ErroComunicacaoMicroservicersException extends Exception {

    @Getter
    private Integer status;

    public ErroComunicacaoMicroservicersException(String msg, Integer status) {
        super(msg);
        this.status = status;
    }
}
