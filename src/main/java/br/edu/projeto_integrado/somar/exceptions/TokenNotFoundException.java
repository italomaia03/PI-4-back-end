package br.edu.projeto_integrado.somar.exceptions;

public class TokenNotFoundException extends NotFoundException {
    public TokenNotFoundException() {
        super("Token inválido");
    }
}
