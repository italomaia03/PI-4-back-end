package br.edu.projeto_integrado.somar.repositories;

import br.edu.projeto_integrado.somar.entities.Inventory;
import br.edu.projeto_integrado.somar.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
    Optional<Inventory> findByUser(User user);
}
