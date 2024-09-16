package br.edu.projeto_integrado.somar.dtos.auth;

import java.io.Serializable;

/**
 * DTO for {@link br.edu.projeto_integrado.somar.entities.Token}
 */
public record RefreshTokenResponse(String accessToken, String refreshToken) implements Serializable {
}