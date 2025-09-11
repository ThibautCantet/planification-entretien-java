package com.soat.planification_entretien.domain.planification;

import java.time.LocalDateTime;


public class Entretien implements IEntretien {
    private Integer id;

    private CandidatSuivi candidat;

    private LocalDateTime horaireEntretien;

    private RecruteurEngagé recruteur;

    public Entretien(Integer id, CandidatSuivi candidat, RecruteurEngagé recruteur, LocalDateTime horaire) {
        this.id = id;
        this.candidat = candidat;
        this.recruteur = recruteur;
        this.horaireEntretien = horaire;
    }

    private Entretien(CandidatSuivi candidat, RecruteurEngagé recruteur, LocalDateTime horaire) {
        this.candidat = candidat;
        this.recruteur = recruteur;
        this.horaireEntretien = horaire;
    }

    public static Entretien of(Integer id, CandidatSuivi candidat, RecruteurEngagé recruteur, LocalDateTime horaire) {
        return new Entretien(id, candidat, recruteur, horaire);
    }

    public static Entretien of(CandidatSuivi candidat, RecruteurEngagé recruteur, LocalDateTime dateEtHeureDisponibiliteDuRecruteur, LocalDateTime dateEtHeureDisponibiliteDuCandidat) {
        if (!recruteur.peutEvaluer(candidat)
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

    public CandidatSuivi getCandidat() {
        return candidat;
    }

    public RecruteurEngagé getRecruteur() {
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
        return candidat.email();
    }

    @Override
    public String getEmailRecruteur() {
        return recruteur.email();
    }

    @Override
    public String getLanguage() {
        return recruteur.profil().langage();
    }

    @Override
    public LocalDateTime getHoraire() {
        return horaireEntretien;
    }
}
