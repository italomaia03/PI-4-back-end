package br.edu.projeto_integrado.somar.controllers.auth;


import br.edu.projeto_integrado.somar.dtos.auth.*;
import br.edu.projeto_integrado.somar.services.auth.SignOutService;
import br.edu.projeto_integrado.somar.services.auth.RefreshTokenService;
import br.edu.projeto_integrado.somar.services.auth.SignInService;
import br.edu.projeto_integrado.somar.services.auth.SignUpService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final SignUpService signUpService;
    private final SignInService signInService;
    private final SignOutService signOutService;
    private final RefreshTokenService refreshTokenService;

    public AuthController(SignUpService signUpService, SignInService signInService, SignOutService signOutService, RefreshTokenService refreshTokenService) {
        this.signUpService = signUpService;
        this.signInService = signInService;
        this.signOutService = signOutService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public SignUpResponse signUp(@RequestBody @Valid SignUpRequest request) {
        return this.signUpService.execute(request);
    }

    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.OK)
    public SignInResponse signIn(@RequestBody @Valid SignInRequest request) {
        return this.signInService.execute(request);
    }

    @PostMapping("/refresh-token")
    @ResponseStatus(HttpStatus.OK)
    public SignInResponse refreshToken(@RequestBody SignOutRequest request) {
        return this.refreshTokenService.execute(request);
    }

    @PostMapping("/sign-out")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void signOut(
            Authentication authentication,
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody SignOutRequest body
    ) {
        this.signOutService.execute(authentication, request, response, body);
    }
}
