package br.edu.projeto_integrado.somar.services.auth;

import br.edu.projeto_integrado.somar.dtos.auth.SignOutRequest;
import br.edu.projeto_integrado.somar.dtos.auth.SignInResponse;
import br.edu.projeto_integrado.somar.entities.Token;
import br.edu.projeto_integrado.somar.exceptions.TokenNotFoundException;
import br.edu.projeto_integrado.somar.repositories.TokenRepository;
import br.edu.projeto_integrado.somar.security.JwtService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RefreshTokenService {
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;

    public RefreshTokenService(JwtService jwtService, TokenRepository tokenRepository) {
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
    }

    public SignInResponse execute(SignOutRequest request) {
        var token =
                this.tokenRepository.findByRefreshToken(request.refreshToken()).orElseThrow(TokenNotFoundException::new);
        var newToken = generateNewToken(token);
        return new SignInResponse(newToken.getAccessToken(), newToken.getRefreshToken());
    }

    private Token generateNewToken(Token token) {
        var user = token.getUser();
        Map<String, Object> extraClaims = Map.of("roles", user.getAuthorities());
        var accessToken = jwtService.generateAccessToken(user, extraClaims);
        var refreshToken = jwtService.generateRefreshToken(user);
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        return this.tokenRepository.save(token);
    }
}
