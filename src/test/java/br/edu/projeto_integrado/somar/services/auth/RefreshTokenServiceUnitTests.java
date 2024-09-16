package br.edu.projeto_integrado.somar.services.auth;

import br.edu.projeto_integrado.somar.dtos.auth.SignOutRequest;
import br.edu.projeto_integrado.somar.entities.Token;
import br.edu.projeto_integrado.somar.entities.User;
import br.edu.projeto_integrado.somar.repositories.TokenRepository;
import br.edu.projeto_integrado.somar.security.JwtService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RefreshTokenServiceUnitTests {

    @Mock
    private JwtService jwtService;
    @Mock
    private TokenRepository tokenRepository;
    @InjectMocks
    private RefreshTokenService service;
    private Token token;
    @BeforeEach
    public void setUp() {
        var user = new User();
        token = new Token();

        token.setAccessToken("accessToken");
        token.setRefreshToken("refreshToken");
        token.setUser(user);
    }

    @Test
    public void executeShouldCreateRefreshTokenWithSuccess() {
        var newToken = new Token();
        newToken.setAccessToken("newAccessToken");
        newToken.setRefreshToken("newRefreshToken");
        when(tokenRepository.findByRefreshToken(Mockito.anyString()))
                .thenReturn(Optional.of(token));
        when(jwtService.generateAccessToken(any(User.class), any()))
                .thenReturn("newAccessToken");
        when(jwtService.generateRefreshToken(Mockito.any()))
                .thenReturn("newRefreshToken");
        when(tokenRepository.save(Mockito.any(Token.class))).thenReturn(token);

        var response = service.execute(new SignOutRequest("refreshToken"));

        verify(tokenRepository, times(1)).save(any(Token.class));
        verify(jwtService, times(1)).generateAccessToken(any(User.class), any());
        verify(jwtService, times(1)).generateRefreshToken(any(User.class));

        Assertions.assertEquals("newAccessToken", response.accessToken());
        Assertions.assertEquals("newRefreshToken", response.refreshToken());
    }
}
