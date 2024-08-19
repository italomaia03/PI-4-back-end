package br.edu.projeto_integrado.somar.exceptions;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException() {
        super("Usuário não encontrado");
    }
}
