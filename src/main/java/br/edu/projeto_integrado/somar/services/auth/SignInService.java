package br.edu.projeto_integrado.somar.services.auth;

import br.edu.projeto_integrado.somar.dtos.auth.SignInRequest;
import br.edu.projeto_integrado.somar.dtos.auth.SignInResponse;
import br.edu.projeto_integrado.somar.dtos.auth.UserLoginResponse;
import br.edu.projeto_integrado.somar.entities.Token;
import br.edu.projeto_integrado.somar.entities.User;
import br.edu.projeto_integrado.somar.repositories.TokenRepository;
import br.edu.projeto_integrado.somar.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SignInService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;

    public SignInService(AuthenticationManager authenticationManager, JwtService jwtService, TokenRepository tokenRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
    }

    public SignInResponse execute(SignInRequest request) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        SecurityContextHolder.getContext().setAuthentication(auth);
        var user = (User) auth.getPrincipal();
        var token = generateToken(user);
        return new SignInResponse(
                token.getAccessToken(),
                token.getRefreshToken(),
                new UserLoginResponse(
                        user.getUuid(),
                        user.getFirstName(),
                        user.getUsername(),
                        user.getImage()
                )
        );
    }

    private Token generateToken(User user) {
        Map<String, Object> extraClaims = Map.of("roles", user.getAuthorities());
        var accessToken = jwtService.generateAccessToken(user, extraClaims);
        var refreshToken = jwtService.generateRefreshToken(user);
        var token = new Token();
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setUser(user);
        return this.tokenRepository.save(token);
    }
}
