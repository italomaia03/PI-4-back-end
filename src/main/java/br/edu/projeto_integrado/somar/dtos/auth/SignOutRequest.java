package br.edu.projeto_integrado.somar.dtos.auth;

import java.io.Serializable;

public record SignOutRequest(String refreshToken) implements Serializable {}
