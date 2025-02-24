package com.example.demo.controller;

import com.example.demo.entities.Prenotazione;
import com.example.demo.service.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/prenotazioni")
public class PrenotazioneController {

    private final PrenotazioneService prenotazioneService;

    @Autowired
    public PrenotazioneController(PrenotazioneService prenotazioneService) {
        this.prenotazioneService = prenotazioneService;
    }

    @PostMapping
    public ResponseEntity<Prenotazione> creaPrenotazione(
            @RequestParam Long utenteId,
            @RequestParam Long eventoId) {

        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(prenotazioneService.creaPrenotazione(utenteId, eventoId));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/utente/{utenteId}")
    public ResponseEntity<List<Prenotazione>> getPrenotazioniUtente(
            @PathVariable Long utenteId) {

        return ResponseEntity.ok(
                Collections.singletonList(prenotazioneService.creaPrenotazione(utenteId, utenteId))
        );
    }

    @GetMapping("/evento/{eventoId}")
    public ResponseEntity<List<Prenotazione>> getPrenotazioniEvento(
            @PathVariable Long eventoId) {

        return ResponseEntity.ok(
                prenotazioneService.getPrenotazioniByEventoId(eventoId)
        );
    }
}