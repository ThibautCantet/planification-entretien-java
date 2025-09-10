package com.soat.planification_entretien.domain.planification;

import java.time.LocalDateTime;

import com.soat.planification_entretien.domain.preparation.Candidat;
import com.soat.planification_entretien.domain.preparation.Recruteur;

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

    public static Entretien of(Integer id, Candidat candidat, Recruteur recruteur, LocalDateTime horaire) {
        return new Entretien(id, candidat, recruteur, horaire);
    }

    public static Entretien of(Candidat candidat, Recruteur recruteur, LocalDateTime dateEtHeureDisponibiliteDuRecruteur, LocalDateTime dateEtHeureDisponibiliteDuCandidat) {
        if (!recruteur.estCompatible(candidat)
            || !dateEtHeureDisponibiliteDuCandidat.equals(dateEtHeureDisponibiliteDuRecruteur)) {
            throw new IllegalArgumentException("Le recruteur n'est pas compatible avec le candidat ou les horaires ne correspondent pas.");
        } else {
            return new Entretien(candidat, recruteur, dateEtHeureDisponibiliteDuRecruteur);
        }
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

    @Override
    public String getEmailCandidat() {
        return candidat.getEmail();
    }

    @Override
    public String getEmailRecruteur() {
        return recruteur.getEmail();
    }

    @Override
    public String getLanguage() {
        return recruteur.getLanguage();
    }

    @Override
    public LocalDateTime getHoraire() {
        return horaireEntretien;
    }
}
