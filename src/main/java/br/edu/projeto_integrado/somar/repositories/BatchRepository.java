package br.edu.projeto_integrado.somar.repositories;

import br.edu.projeto_integrado.somar.entities.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Integer> {
    Optional<Batch> findByBarcode(String barcode);
}
