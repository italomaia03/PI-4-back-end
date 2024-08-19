package br.edu.projeto_integrado.somar.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends AbstractException {

    protected NotFoundException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

}
