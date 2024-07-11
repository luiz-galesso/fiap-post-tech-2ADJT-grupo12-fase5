package com.fase5.techchallenge.fiap.msusuario.usecase.exception;

public class BussinessErrorException extends RuntimeException {
    public BussinessErrorException(String message) {
        super(message);
    }
    public BussinessErrorException() {

    }
}

