package com.soat.planification_entretien.entretien.domain;

import java.time.LocalDateTime;

public class Entretien implements IEntretien {
    private EntretienId id;

    private Candidat candidat;

    private LocalDateTime horaireEntretien;

    private RecruteurPlanifié recruteur;

    public Entretien(Integer id, Candidat candidat, RecruteurPlanifié recruteur, LocalDateTime horaire) {
        this.id = new EntretienId(id);
        this.candidat = candidat;
        this.recruteur = recruteur;
        this.horaireEntretien = horaire;
    }

    private Entretien(Candidat candidat, RecruteurPlanifié recruteur, LocalDateTime horaire) {
        this.candidat = candidat;
        this.recruteur = recruteur;
        this.horaireEntretien = horaire;
    }

    public Entretien(Candidat candidat, RecruteurPlanifié recruteur) {
        this.candidat = candidat;
        this.recruteur = recruteur;
    }

    public static Entretien of(Integer id, Candidat candidat, RecruteurPlanifié recruteur, LocalDateTime horaire) {
        return new Entretien(id, candidat, recruteur, horaire);
    }

    public static Entretien of(Candidat candidat, RecruteurPlanifié recruteur, LocalDateTime dateEtHeureDisponibiliteDuRecruteur) {
        return new Entretien(candidat, recruteur, dateEtHeureDisponibiliteDuRecruteur);
    }

    public static Entretien of(Integer newId, Entretien entretien) {
        entretien.id = new EntretienId(newId);
        return entretien;
    }

    public int getCandidatId() {
        return candidat.id();
    }

    public int getRecruteurId() {
        return recruteur.id();
    }

    public LocalDateTime getHoraireEntretien() {
        return horaireEntretien;
    }

    public Integer getId() {
        return Integer.valueOf(id.value());
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
        return recruteur.langage();
    }

    @Override
    public LocalDateTime getHoraire() {
        return horaireEntretien;
    }

    public boolean planifier(LocalDateTime dateEtHeureDisponibiliteDuCandidat, LocalDateTime dateEtHeureDisponibiliteDuRecruteur) {
        if (recruteur.estCompatibleAvec(candidat)) {
            if (dateEtHeureDisponibiliteDuCandidat.equals(dateEtHeureDisponibiliteDuRecruteur)) {
                this.horaireEntretien = dateEtHeureDisponibiliteDuCandidat;
                return true;
            }
        }
        return false;
    }
}
