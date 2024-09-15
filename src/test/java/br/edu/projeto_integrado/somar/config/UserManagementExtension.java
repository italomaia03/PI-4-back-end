package br.edu.projeto_integrado.somar.config;

import br.edu.projeto_integrado.somar.entities.Inventory;
import br.edu.projeto_integrado.somar.entities.User;
import br.edu.projeto_integrado.somar.repositories.UserRepository;
import br.edu.projeto_integrado.somar.security.JwtService;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

public class UserManagementExtension implements BeforeAllCallback {

    private static String userToken;

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        var appContext = SpringExtension.getApplicationContext(extensionContext);
        var userRepository = appContext.getBean("userRepository", UserRepository.class);
        var jwtService = appContext.getBean("jwtService", JwtService.class);
        var inventoryManagement = appContext.getBean("inventoryManagement", InventoryManagement.class);
        var encoder = new BCryptPasswordEncoder();

        var user = registerUser(userRepository, encoder);
        inventoryManagement.createBatchesAndProducts(user.getInventory());

        userToken = jwtService.generateAccessToken(user);
    }

    private User registerUser(UserRepository repository, BCryptPasswordEncoder encoder) {
        var user = new User();
        user.setUuid(UUID.fromString("8c0dcc19-70ac-411a-b0a6-cd09741e9e59"));
        user.setEmail("test@test.com");
        user.setPassword(encoder.encode("test@1234"));
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setActive(true);

        var inventory = new Inventory();
        inventory.setUser(user);

        user.setInventory(new Inventory());
        return repository.save(user);
    }

    public static String getUserToken() {
        return userToken;
    }
}
