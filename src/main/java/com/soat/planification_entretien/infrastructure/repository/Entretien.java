package com.soat.planification_entretien.infrastructure.repository;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.soat.planification_entretien.entretien.command.application.controller.Status;

@Entity
public class Entretien {
    @Id
    private String id;

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

    private Entretien(String id, Candidat candidat, Recruteur recruteur, LocalDateTime horaire, int status) {
        this.id = id;
        this.candidat = candidat;
        this.recruteur = recruteur;
        this.horaireEntretien = horaire;
        this.status = status;
    }

    public Entretien() {

    }

    public static Entretien of(String id, Candidat candidat, Recruteur recruteur, LocalDateTime horaire, int status) {
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

    public String getId() {
        return id;
    }

    public Status getStatus() {
        return Status.of(status);
    }
}
