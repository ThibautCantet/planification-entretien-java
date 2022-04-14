package com.soat.planification_entretien.domain;

import java.time.LocalDateTime;

public class Entretien {
    private Integer id;

    private Candidat candidat;

    private LocalDateTime horaireEntretien;

    private Recruteur recruteur;

    private Entretien(Candidat candidat, Recruteur recruteur, LocalDateTime horaire) {
        this.candidat = candidat;
        this.recruteur = recruteur;
        this.horaireEntretien = horaire;
    }

    public Entretien() {

    }

    public static Entretien of(Candidat candidat, Recruteur recruteur, LocalDateTime horaire) {
        return new Entretien(candidat, recruteur, horaire);
    }

    public Integer getId() {
        return id;
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
}
