package com.soat.planification_entretien.entretien.infrastructure.repository;

import java.time.LocalDateTime;

import com.soat.planification_entretien.candidat.infrastructure.repository.Candidat;
import com.soat.planification_entretien.recruteur.infrastructure.repository.Recruteur;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Entretien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "candidat_id")
    private Candidat candidat;

    @Column
    private LocalDateTime horaireEntretien;

    @Column
    private String status;

    @ManyToOne
    @JoinColumn(name = "recruteur_id")
    private Recruteur recruteur;

    private Entretien(Integer id, Candidat candidat, Recruteur recruteur, LocalDateTime horaire, String status) {
        this.id = id;
        this.candidat = candidat;
        this.recruteur = recruteur;
        this.horaireEntretien = horaire;
        this.status = status;
    }

    public Entretien() {

    }

    public static Entretien of(Integer id, Candidat candidat, Recruteur recruteur, LocalDateTime horaire, String status) {
        return new Entretien(id, candidat, recruteur, horaire, status);
    }

    public Candidat getCandidat() {
        return candidat;
    }

    public Recruteur getRecruteur() {
        return recruteur;
    }

    public LocalDateTime getHoraireEntretien() {
        return horaireEntretien;
    }

    public Integer getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}
