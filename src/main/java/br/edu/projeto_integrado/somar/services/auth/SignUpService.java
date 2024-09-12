package br.edu.projeto_integrado.somar.services.auth;

import br.edu.projeto_integrado.somar.dtos.auth.SignUpRequest;
import br.edu.projeto_integrado.somar.dtos.auth.SignUpResponse;
import br.edu.projeto_integrado.somar.entities.Inventory;
import br.edu.projeto_integrado.somar.entities.Role;
import br.edu.projeto_integrado.somar.entities.User;
import br.edu.projeto_integrado.somar.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SignUpService {
    private final UserRepository repository;

    public SignUpService(UserRepository repository) {
        this.repository = repository;
    }

    public SignUpResponse execute(SignUpRequest request) {
        var user = mapToUser(request);
        user = repository.save(user);
        return mapToResponse(user);
    }

    private User mapToUser(SignUpRequest signUpRequest) {
        var user = new User();
        var inventory = new Inventory();
        var uuid = UUID.randomUUID();
        var password = encryptPassword(signUpRequest.getPassword());
        user.setUuid(uuid);
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(password);
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setActive(true);
        user.setRole(Role.USER);
        return user.setInventory(inventory);
    }

    private String encryptPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    private SignUpResponse mapToResponse(User user) {
        return new SignUpResponse(
                user.getFirstName(),
                user.getUsername(),
                user.getUuid()
        );
    }
}