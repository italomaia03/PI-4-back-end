package br.edu.projeto_integrado.somar.dtos.auth;

import java.util.Map;

public record SignInResponse(String accessToken, String refreshToken) {
}
