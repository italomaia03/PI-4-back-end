package br.edu.projeto_integrado.somar.services.inventory;

import br.edu.projeto_integrado.somar.entities.Inventory;
import br.edu.projeto_integrado.somar.entities.User;
import br.edu.projeto_integrado.somar.exceptions.UserNotFoundException;
import br.edu.projeto_integrado.somar.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetInventoryServiceUnitTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private GetInventoryService service;
    private User user;
    private Inventory inventory;

    @BeforeEach
    public void setUp() {
        user = new User();
        inventory = new Inventory();
        user.setInventory(inventory);
    }

    @Test
    public void getInventoryShouldReturnInventorySuccessfully() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        var result = service.execute("test@test.com");

        verify(userRepository, times(1)).findByEmail(anyString());
        Assertions.assertEquals(inventory, result);
    }

    @Test
    public void getInventoryShouldThrowExceptionWhenUserNotFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () -> service.execute("test@test.com"));
    }
}
