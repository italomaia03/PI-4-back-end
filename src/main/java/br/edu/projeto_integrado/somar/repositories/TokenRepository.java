package br.edu.projeto_integrado.somar.repositories;

import br.edu.projeto_integrado.somar.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
    Optional<Token> findByRefreshToken(String token);
    void deleteByRefreshToken(String token);
}
