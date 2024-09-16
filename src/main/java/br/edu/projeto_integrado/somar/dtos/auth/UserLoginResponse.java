package br.edu.projeto_integrado.somar.dtos.auth;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link br.edu.projeto_integrado.somar.entities.User}
 */
public record UserLoginResponse(UUID uuid, String email, String firstName, String image) implements Serializable {
}