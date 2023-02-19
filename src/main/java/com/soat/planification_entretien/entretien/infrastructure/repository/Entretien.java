package com.soat.planification_entretien.entretien.infrastructure.repository;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.soat.planification_entretien.candidat.infrastructure.repository.Candidat;
import com.soat.planification_entretien.recruteur.infrastructure.repository.Recruteur;

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

    @ManyToOne
    @JoinColumn(name = "recruteur_id")
    private Recruteur recruteur;

    @Column
    private int status;

    private Entretien(Candidat candidat, Recruteur recruteur, LocalDateTime horaire, int status) {
        this.candidat = candidat;
        this.recruteur = recruteur;
        this.horaireEntretien = horaire;
        this.status = status;
    }

    public Entretien() {

    }

    public static Entretien of(Candidat candidat, Recruteur recruteur, LocalDateTime horaire, int status) {
        return new Entretien(candidat, recruteur, horaire, status);
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

    public int getStatus() {
        return status;
    }

    public void setCandidat(Candidat candidat) {
        this.candidat = candidat;
    }

    public void setHoraireEntretien(LocalDateTime horaireEntretien) {
        this.horaireEntretien = horaireEntretien;
    }

    public void setRecruteur(Recruteur recruteur) {
        this.recruteur = recruteur;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}