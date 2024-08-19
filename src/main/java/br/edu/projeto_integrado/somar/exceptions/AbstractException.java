package br.edu.projeto_integrado.somar.exceptions;

import org.springframework.http.HttpStatus;

public abstract class AbstractException extends RuntimeException {
    protected AbstractException(String message) {
        super(message);
    }

    public abstract HttpStatus getHttpStatus();
}
