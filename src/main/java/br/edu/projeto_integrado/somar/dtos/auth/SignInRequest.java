package br.edu.projeto_integrado.somar.dtos.auth;

import jakarta.validation.constraints.Email;

public class SignInRequest {
    @Email
    private String email;
    private String password;

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }
}
