package com.soat.planification_entretien.model;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Entretien {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "candidat_id")
    private Candidat candidat;

    @ManyToOne
    @JoinColumn(name = "recruteur_id")
    private Recruteur recruteur;

    @Column
    private LocalDateTime horaire;

    public Entretien() {
    }

    public Entretien(Candidat candidat, Recruteur recruteur, Disponibilite horaire, int id) {
        this.candidat = candidat;
        this.recruteur = recruteur;
        this.horaire = horaire.dateTime();
        this.id = id;
    }

    public Entretien(Candidat candidat, Recruteur recruteur, Disponibilite horaire) {
        this.candidat = candidat;
        this.recruteur = recruteur;
        this.horaire = horaire.dateTime();
    }
}
