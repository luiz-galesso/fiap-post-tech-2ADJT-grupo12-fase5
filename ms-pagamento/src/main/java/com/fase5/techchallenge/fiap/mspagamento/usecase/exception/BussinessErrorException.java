package com.fase5.techchallenge.fiap.mspagamento.usecase.exception;

public class BussinessErrorException extends RuntimeException {
    public BussinessErrorException(String message) {
        super(message);
    }
    public BussinessErrorException() {

    }
}

