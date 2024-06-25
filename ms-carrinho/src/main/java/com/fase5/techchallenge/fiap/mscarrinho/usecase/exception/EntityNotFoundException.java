package com.fase5.techchallenge.fiap.mscarrinho.usecase.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
    public EntityNotFoundException() {

    }
}

