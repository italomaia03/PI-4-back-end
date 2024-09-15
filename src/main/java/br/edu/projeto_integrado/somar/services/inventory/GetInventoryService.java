package br.edu.projeto_integrado.somar.services.inventory;

import br.edu.projeto_integrado.somar.entities.Inventory;
import br.edu.projeto_integrado.somar.exceptions.UserNotFoundException;
import br.edu.projeto_integrado.somar.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class GetInventoryService {
    private final UserRepository userRepository;

    public GetInventoryService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Inventory execute(String username) {
        return userRepository.findByEmail(username)
                .orElseThrow(UserNotFoundException::new)
                .getInventory();
    }
}
