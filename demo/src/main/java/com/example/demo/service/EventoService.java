package com.example.demo.service;

import com.example.demo.entities.Evento;
import com.example.demo.entities.Ruolo;
import com.example.demo.entities.Utente;
import com.example.demo.repository.EventoRepository;
import com.example.demo.repository.UtenteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    // Crea un nuovo evento (solo ADMIN o MANAGER possono farlo)
    @Transactional
    public Evento creaEvento(Evento evento, Long creatoreId, Ruolo richiedenteRuolo) {
        if (richiedenteRuolo != Ruolo.ADMIN && richiedenteRuolo != Ruolo.MANAGER) {
            throw new SecurityException("Solo admin o manager possono creare eventi");
        }

        Utente creatore = utenteRepository.findById(creatoreId)
                .orElseThrow(() -> new RuntimeException("Creatore non trovato"));

        evento.setCreatore(creatore);
        return eventoRepository.save(evento);
    }

    // Modifica un evento esistente (solo il creatore o l'admin possono farlo)
    @Transactional
    public Evento modificaEvento(Long id, Evento eventoAggiornato, Long utenteId, Ruolo ruolo) {
        Evento eventoEsistente = eventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento non trovato"));

        // Controlla se l'utente è il creatore o un admin
        if (!Objects.equals(eventoEsistente.getCreatore().getId(), utenteId) && ruolo != Ruolo.ADMIN) {
            throw new SecurityException("Non autorizzato a modificare questo evento");
        }

        // Aggiorna i campi dell'evento
        eventoEsistente.setTitolo(eventoAggiornato.getTitolo());
        eventoEsistente.setDescrizione(eventoAggiornato.getDescrizione());
        eventoEsistente.setDataEvento(eventoAggiornato.getDataEvento());
        eventoEsistente.setLuogo(eventoAggiornato.getLuogo());
        eventoEsistente.setPostiDisponibili(eventoAggiornato.getPostiDisponibili());

        return eventoRepository.save(eventoEsistente);
    }

    // Elimina un evento (solo il creatore o l'admin possono farlo)
    @Transactional
    public void eliminaEvento(Long id, Long utenteId, Ruolo ruolo) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento non trovato"));

        // Controlla se l'utente è il creatore o un admin
        if (!Objects.equals(evento.getCreatore().getId(), utenteId) && ruolo != Ruolo.ADMIN) {
            throw new SecurityException("Non autorizzato a eliminare questo evento");
        }

        eventoRepository.delete(evento);
    }

    // Recupera tutti gli eventi
    public List<Evento> getAllEventi() {
        return eventoRepository.findAll();
    }

    // Recupera un evento per ID
    public Evento getEventoById(Long id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento non trovato"));
    }
}