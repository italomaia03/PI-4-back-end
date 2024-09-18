package br.edu.projeto_integrado.somar.services.auth;

import br.edu.projeto_integrado.somar.dtos.auth.SignUpRequest;
import br.edu.projeto_integrado.somar.entities.User;
import br.edu.projeto_integrado.somar.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SignUpUnitTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SignUpService signUpService;

    @Test
    public void signUpShouldSucceedWhenValidDataIsProvided() {
        var uuid = UUID.randomUUID();

        var user = new User();
        user.setFirstName("Test");
        user.setEmail("test@email.com");
        user.setUuid(uuid);
        user.setId(1);

        var data = new SignUpRequest();
        data.setEmail("test@email.com");
        data.setPassword("password");
        data.setFirstName("Test");
        data.setLastName("TestLastName");

        when(userRepository.save(any(User.class))).thenReturn(user);

        var result = signUpService.execute(data);

        assertEquals("test@email.com", result.email());
        assertEquals("Test", result.firstName());
        assertEquals(uuid, result.uuid());
    }
}
