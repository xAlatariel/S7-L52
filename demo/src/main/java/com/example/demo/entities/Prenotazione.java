package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity
public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "utente_id", nullable = false)
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    private LocalDateTime dataPrenotazione = LocalDateTime.now();

    public Prenotazione(Long id, Utente utente, Evento evento, LocalDateTime dataPrenotazione) {
        this.id = id;
        this.utente = utente;
        this.evento = evento;
        this.dataPrenotazione = dataPrenotazione;
    }

    public Prenotazione() {

    }

    public void setUtente(Utente utente) {
    }

    public void setEvento(Evento evento) {
    }
}