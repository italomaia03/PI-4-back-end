package br.edu.projeto_integrado.somar.repositories;

import br.edu.projeto_integrado.somar.entities.Batch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BatchRepository extends JpaRepository<Batch, Integer> {
    Optional<Batch> findByBarcode(String barcode);
}
