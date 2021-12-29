package com.soat.planification_entretien.model;

import java.time.LocalDate;

public class Entretien {
    private final Candidat candidat;
    private final Recruteur recruteur;
    private HoraireEntretien horaireEntretien;

    public Entretien(Candidat candidat, Recruteur recruteur) {
        this.candidat = candidat;
        this.recruteur = recruteur;
    }

    private Entretien(Candidat candidat, Recruteur recruteur, HoraireEntretien horaire) {
        this.candidat = candidat;
        this.recruteur = recruteur;
        this.horaireEntretien = horaire;
    }

    public static Entretien of(Candidat candidat, Recruteur recruteur, HoraireEntretien horaire) {
        return new Entretien(candidat, recruteur, horaire);
    }

    public Candidat getCandidat() {
        return candidat;
    }

    public ResultatPlanificationEntretien planifier(Disponibilite disponibiliteDuCandidat, LocalDate dateDeDisponibiliteDuRecruteur) {
        this.horaireEntretien  = new HoraireEntretien(disponibiliteDuCandidat.horaire());
        if (disponibiliteDuCandidat.verifier(dateDeDisponibiliteDuRecruteur)) {
            return new EntretienPlanifie(candidat, recruteur, horaireEntretien);
        } else {
            return new EntretienEchouee(candidat, recruteur, horaireEntretien);
        }
    }
}
