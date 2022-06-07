package com.soat.planification_entretien.domain.entretien.command.entity;

import java.time.LocalDateTime;

import com.soat.planification_entretien.domain.entretien.query.dto.IEntretien;

public class Entretien implements IEntretien {
    private Integer id;

    private Candidat candidat;

    private LocalDateTime horaireEntretien;

    private Recruteur recruteur;

    public Entretien(Integer id, Candidat candidat, Recruteur recruteur, LocalDateTime horaire) {
        this.id = id;
        this.candidat = candidat;
        this.recruteur = recruteur;
        this.horaireEntretien = horaire;
    }

    private Entretien(Candidat candidat, Recruteur recruteur, LocalDateTime horaire) {
        this.candidat = candidat;
        this.recruteur = recruteur;
        this.horaireEntretien = horaire;
    }

    public Entretien(Candidat candidat, Recruteur recruteur) {
        this.candidat = candidat;
        this.recruteur = recruteur;
    }

    public static Entretien of(Integer id, Candidat candidat, Recruteur recruteur, LocalDateTime horaire) {
        return new Entretien(id, candidat, recruteur, horaire);
    }

    public static Entretien of(Candidat candidat, Recruteur recruteur, LocalDateTime dateEtHeureDisponibiliteDuRecruteur) {
        return new Entretien(candidat, recruteur, dateEtHeureDisponibiliteDuRecruteur);
    }

    public static Entretien of(Integer newId, Entretien entretien) {
        entretien.id = newId;
        return entretien;
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
        Creneau creneau = new Creneau(dateEtHeureDisponibiliteDuRecruteur, dateEtHeureDisponibiliteDuCandidat);
        boolean planifiable = recruteur.peutTester(candidat)
                && creneau.estDisponible();

        if (planifiable) {
            horaireEntretien = dateEtHeureDisponibiliteDuCandidat;
        }

        return planifiable;
    }

    @Override
    public String getEmailCandidat() {
        return candidat.email();
    }

    @Override
    public String getEmailRecruteur() {
        return recruteur.email();
    }

    @Override
    public String getLanguage() {
        return recruteur.language();
    }

    @Override
    public LocalDateTime getHoraire() {
        return horaireEntretien;
    }
}
