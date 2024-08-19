package br.edu.projeto_integrado.somar.dtos.auth;

import java.util.UUID;

public record SignUpResponse (String firstName, String email, UUID uuid) {}
