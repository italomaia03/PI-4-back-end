package br.edu.projeto_integrado.somar.services.auth;

import br.edu.projeto_integrado.somar.dtos.auth.SignOutRequest;
import br.edu.projeto_integrado.somar.repositories.TokenRepository;
import br.edu.projeto_integrado.somar.repositories.UserRepository;
import br.edu.projeto_integrado.somar.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

@Service
public class SignOutService {
    private final TokenRepository tokenRepository;
    private final SecurityContextLogoutHandler logoutHandler;

    public SignOutService(TokenRepository tokenRepository, SecurityContextLogoutHandler logoutHandler, JwtService jwtService, UserRepository userRepository) {
        this.tokenRepository = tokenRepository;
        this.logoutHandler = logoutHandler;
    }

    @Transactional
    public void execute(Authentication authentication, HttpServletRequest request, HttpServletResponse response,
                        SignOutRequest body) {
        var refreshToken = body.refreshToken();
        this.tokenRepository.deleteByRefreshToken(refreshToken);
        logoutHandler.logout(request, response, authentication);
    }
}
