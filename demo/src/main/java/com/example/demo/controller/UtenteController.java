package com.example.demo.controller;

import com.example.demo.entities.Ruolo;
import com.example.demo.entities.Utente;
import com.example.demo.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/utenti")
public class UtenteController {

    private final UtenteService utenteService;

    @Autowired
    public UtenteController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @PostMapping
    public ResponseEntity<Utente> registrazione(@RequestBody Utente utente) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(utenteService.registraUtente(utente));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Utente> getUtente(@PathVariable Long id) {
        return ResponseEntity.ok(utenteService.getUtenteById(id));
    }

    @PatchMapping("/{id}/ruolo")
    public ResponseEntity<Utente> aggiornaRuolo(
            @PathVariable Long id,
            @RequestParam Ruolo nuovoRuolo,
            @RequestHeader("X-Utente-Ruolo") Ruolo richiedenteRuolo) {

        try {
            return ResponseEntity.ok(
                    utenteService.aggiornaRuoloUtente(id ,nuovoRuolo,richiedenteRuolo)
            );
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}