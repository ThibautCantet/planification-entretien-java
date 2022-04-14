package com.soat.planification_entretien.domain;

import java.time.LocalDateTime;

public class Entretien {
    private Integer id;

    private Candidat candidat;

    private LocalDateTime horaireEntretien;

    private Recruteur recruteur;

    private Entretien(int id, Candidat candidat, Recruteur recruteur, LocalDateTime horaire) {
        this.id = id;
        this.candidat = candidat;
        this.recruteur = recruteur;
        this.horaireEntretien = horaire;
    }

    private Entretien(Candidat candidat, Recruteur recruteur, LocalDateTime horaire) {
        this(0, candidat, recruteur, horaire);
    }

    public static Entretien of(Candidat candidat, Recruteur recruteur, LocalDateTime horaire) {
        return new Entretien(candidat, recruteur, horaire);
    }

    public static Entretien of(int id, Candidat candidat, Recruteur recruteur, LocalDateTime horaire) {
        return new Entretien(id, candidat, recruteur, horaire);
    }

    public static Entretien of(int id, Entretien entretien) {
        return new Entretien(id, entretien.getCandidat(), entretien.getRecruteur(), entretien.getHoraireEntretien());
    }

    public Candidat getCandidat() {
        return candidat;
    }

    public LocalDateTime getHoraireEntretien() {
        return horaireEntretien;
    }

    public Recruteur getRecruteur() {
        return recruteur;
    }
}
