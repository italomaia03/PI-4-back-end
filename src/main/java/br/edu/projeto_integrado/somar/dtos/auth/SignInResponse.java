package br.edu.projeto_integrado.somar.dtos.auth;

public record SignInResponse(
        String accessToken,
        String refreshToken,
        UserLoginResponse user
) {}
