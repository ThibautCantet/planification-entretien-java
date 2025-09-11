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
    }

    public static Entretien of(Integer id, CandidatSuivi candidat, RecruteurEngagé recruteur, LocalDateTime horaire) {
        return new Entretien(id, candidat, recruteur, horaire);
    }

    public static Entretien of(CandidatSuivi candidat, RecruteurEngagé recruteur) {
        return new Entretien(null, candidat, recruteur, null);
    }

    public static Entretien of(CandidatSuivi candidat, RecruteurEngagé recruteur, LocalDateTime horaireEntretien) {
        return new Entretien(null, candidat, recruteur, horaireEntretien);
    }

    public static Entretien of(Integer newId, Entretien entretien) {
        entretien.id = newId;
        return entretien;
    }

    public void planifier(LocalDateTime dateEtHeureDisponibiliteDuCandidat, LocalDateTime dateEtHeureDisponibiliteDuRecruteur) {
        if (!recruteur.peutEvaluer(candidat)
            || !dateEtHeureDisponibiliteDuCandidat.equals(dateEtHeureDisponibiliteDuRecruteur)) {
            throw new IllegalArgumentException("Le recruteur n'est pas compatible avec le candidat ou les horaires ne correspondent pas.");
        }

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
