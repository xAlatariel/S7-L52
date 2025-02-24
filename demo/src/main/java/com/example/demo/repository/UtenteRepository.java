package com.example.demo.repository;

import com.example.demo.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtenteRepository extends JpaRepository<Utente, Long> {
    boolean findByUsername(String username);
}
