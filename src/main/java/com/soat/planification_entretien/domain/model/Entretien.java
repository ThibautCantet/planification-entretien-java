package com.soat.planification_entretien.domain.model;


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

    public Entretien(Integer id, Candidat candidat, Recruteur recruteur, LocalDateTime horaireEntretien) {
        this.id = id;
        this.candidat = candidat;
        this.recruteur = recruteur;
        this.horaireEntretien = horaireEntretien;
    }

    public static Entretien of(Candidat candidat, Recruteur recruteur) {
        return new Entretien(candidat, recruteur, null);
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

    public boolean planifier(LocalDateTime dateEtHeureDisponibiliteDuCandidat, LocalDateTime dateEtHeureDisponibiliteDuRecruteur) {
        return recruteur.getLanguage().equals(candidat.getLanguage())
            && recruteur.getExperienceInYears() > candidat.getExperienceInYears()
            && dateEtHeureDisponibiliteDuCandidat.equals(dateEtHeureDisponibiliteDuRecruteur);
    }
}
