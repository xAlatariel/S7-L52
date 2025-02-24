package com.example.demo.controller;

import com.example.demo.entities.Evento;
import com.example.demo.entities.Ruolo;
import com.example.demo.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/eventi")
public class EventoController {

    private final EventoService eventoService;

    @Autowired
    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @PostMapping
    public ResponseEntity<Evento> creaEvento(
            @RequestBody Evento evento,
            @RequestHeader("X-Utente-Id") Long creatoreId,
            @RequestHeader("X-Utente-Ruolo") Ruolo richiedenteRuolo) {

        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(eventoService.creaEvento(evento, creatoreId, richiedenteRuolo));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evento> modificaEvento(
            @PathVariable Long id,
            @RequestBody Evento eventoAggiornato,
            @RequestHeader("X-Utente-Id") Long utenteId,
            @RequestHeader("X-Utente-Ruolo") Ruolo ruolo) {

        try {
            return ResponseEntity.ok(
                    eventoService.modificaEvento(id, eventoAggiornato, utenteId, ruolo)
            );
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminaEvento(
            @PathVariable Long id,
            @RequestHeader("X-Utente-Id") Long utenteId,
            @RequestHeader("X-Utente-Ruolo") Ruolo ruolo) {

        try {
            eventoService.eliminaEvento(id, utenteId, ruolo);
            return ResponseEntity.noContent().build();
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}