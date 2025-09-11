package com.soat.planification_entretien.domain.planification;

import java.time.LocalDateTime;


public class Entretien implements IEntretien {
    private Integer id;

    private CandidatSuivi candidat;

    private LocalDateTime horaireEntretien;

    private RecruteurEngagé recruteur;

    private EtatEntretien etat;

    private Entretien(Integer id, CandidatSuivi candidat, RecruteurEngagé recruteur, LocalDateTime horaire,EtatEntretien etat) {
        this.id = id;
        this.candidat = candidat;
        this.recruteur = recruteur;
        this.horaireEntretien = horaire;
        this.etat = etat;
    }


    public static Entretien of(Integer id, CandidatSuivi candidat, RecruteurEngagé recruteur, LocalDateTime horaire, EtatEntretien etat) {
        return new Entretien(id, candidat, recruteur, horaire, etat);
    }

    public static Entretien create(CandidatSuivi candidat, RecruteurEngagé recruteur) {
        return new Entretien(null, candidat, recruteur, null, EtatEntretien.BROUILLON);
    }

    public static Entretien of(CandidatSuivi candidat, RecruteurEngagé recruteur, LocalDateTime horaireEntretien, EtatEntretien etat) {
        return new Entretien(null, candidat, recruteur, horaireEntretien, etat);
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
        changementEtat(EtatEntretien.PLANIFIE);
        this.horaireEntretien = dateEtHeureDisponibiliteDuRecruteur;
    }

    public void valider() {
        this.changementEtat(EtatEntretien.VALIDE);
    }

    private void changementEtat(EtatEntretien nouvelEtat) {
        if (!etat.peutEvoluerVers(nouvelEtat)) {
            throw new IllegalStateException("L'entretien ne peut pas évoluer vers l'état " + nouvelEtat + " depuis l'état " + etat);
        }
        this.etat = nouvelEtat;
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

    @Override
    public String getStatus() {
        return etat.name();
    }
}
