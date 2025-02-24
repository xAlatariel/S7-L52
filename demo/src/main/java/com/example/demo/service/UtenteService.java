package com.example.demo.service;

import com.example.demo.entities.Ruolo;
import com.example.demo.entities.Utente;
import com.example.demo.repository.UtenteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    // Registra un nuovo utente con ruolo USER di default
    public Utente registraUtente(Utente utente) {
        if (utenteRepository.findByUsername(utente.getUsername())) {
            throw new RuntimeException("Username già esistente");
        }
        utente.setRuolo(Ruolo.USER); // Ruolo di default
        return utenteRepository.save(utente);
    }

    // Recupera un utente per ID
    public Utente getUtenteById(Long id) {
        return utenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
    }

    // Aggiorna il ruolo di un utente solo admin
    @Transactional
    public Utente aggiornaRuoloUtente(Long id, Ruolo nuovoRuolo, Ruolo richiedenteRuolo) {
        if (richiedenteRuolo != Ruolo.ADMIN) {
            throw new SecurityException("Solo l'admin può modificare i ruoli");
        }

        Utente utente = utenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        utente.setRuolo(nuovoRuolo);
        return utenteRepository.save(utente);
    }

    // Metodo per ottenere tutti gli utenti
    public List<Utente> getAllUtenti() {
        return utenteRepository.findAll();
    }
}