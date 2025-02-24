package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titolo;
    private String descrizione;
    private LocalDateTime dataEvento;
    private String luogo;

    @Column(name = "posti_disponibili")
    private int postiDisponibili;

    @ManyToOne
    @JoinColumn(name = "creatore_id", nullable = false)
    private Utente creatore;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL)
    private List<Prenotazione> prenotazioni = new ArrayList<>();


//    public Evento(Long id, String titolo, String descrizione, LocalDateTime dataEvento, String luogo, int postiDisponibili, Utente creatore) {
//        this.id = id;
//        this.titolo = titolo;
//        this.descrizione = descrizione;
//        this.dataEvento = dataEvento;
//        this.luogo = luogo;
//        this.postiDisponibili = postiDisponibili;
//        this.creatore = creatore;
//    }


    public int getPostiDisponibili() {
        return 0;
    }

    public void setPostiDisponibili(int i) {
    }

    public void setCreatore(Utente creatore) {
    }

    public Thread getCreatore() {
        return null;
    }
}