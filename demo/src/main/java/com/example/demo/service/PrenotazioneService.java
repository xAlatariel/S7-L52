package com.example.demo.service;

import com.example.demo.entities.Evento;
import com.example.demo.entities.Prenotazione;
import com.example.demo.entities.Utente;
import com.example.demo.repository.EventoRepository;
import com.example.demo.repository.PrenotazioneRepository;
import com.example.demo.repository.UtenteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrenotazioneService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Transactional
    public Prenotazione creaPrenotazione(Long utenteId, Long eventoId) {
        Utente utente = utenteRepository.findById(utenteId)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento non trovato"));

        if (evento.getPostiDisponibili() <= 0) {
            throw new IllegalStateException("Posti esauriti");
        }

        evento.setPostiDisponibili(evento.getPostiDisponibili() - 1);
        eventoRepository.save(evento);

        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setUtente(utente);
        prenotazione.setEvento(evento);

        return prenotazioneRepository.save(prenotazione);
    }

    public List<Prenotazione> getPrenotazioniByEventoId(Long eventoId) {
        return List.of();
    }
}