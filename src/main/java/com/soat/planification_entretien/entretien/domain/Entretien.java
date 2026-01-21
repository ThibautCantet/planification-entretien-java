package com.soat.planification_entretien.entretien.domain;

import java.time.LocalDateTime;

public class Entretien implements IEntretien {
    private EntretienId id;

    private Candidat candidat;

    private LocalDateTime horaireEntretien;

    private RecruteurPlanifié recruteur;
    private StatusEntretien status;

    private Entretien(Integer id, Candidat candidat, RecruteurPlanifié recruteur, LocalDateTime horaire, String status) {
        this.id = new EntretienId(id);
        this.candidat = candidat;
        this.recruteur = recruteur;
        this.horaireEntretien = horaire;
        this.status = status != null && !status.isEmpty() ? StatusEntretien.valueOf(status.toUpperCase()) : null;
    }

    private Entretien(Candidat candidat, RecruteurPlanifié recruteur, LocalDateTime horaire, String status) {
        this(null, candidat, recruteur, horaire, status);
    }

    public Entretien(Candidat candidat, RecruteurPlanifié recruteur) {
        this.candidat = candidat;
        this.recruteur = recruteur;
    }

    public static Entretien of(Integer id, Candidat candidat, RecruteurPlanifié recruteur, LocalDateTime horaire, String status) {
        return new Entretien(id, candidat, recruteur, horaire, status);
    }

    public static Entretien of(Candidat candidat, RecruteurPlanifié recruteur, LocalDateTime dateEtHeureDisponibiliteDuRecruteur, String status) {
        return new Entretien(candidat, recruteur, dateEtHeureDisponibiliteDuRecruteur, status);
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
        return this.id != null && id.value() != null && !id.value().isEmpty() ? Integer.valueOf(id.value()) : null;
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

    @Override
    public String getStatus() {
        return status != null ? status.name() : null;
    }

    public boolean planifier(LocalDateTime dateEtHeureDisponibiliteDuCandidat, LocalDateTime dateEtHeureDisponibiliteDuRecruteur) {
        if (recruteur.estCompatibleAvec(candidat)) {
            if (dateEtHeureDisponibiliteDuCandidat.equals(dateEtHeureDisponibiliteDuRecruteur)) {
                this.horaireEntretien = dateEtHeureDisponibiliteDuCandidat;
                this.status = StatusEntretien.PLANIFIE;
                return true;
            }
        }
        return false;
    }

    public boolean valider() {
        this.status = StatusEntretien.VALIDE;
        return true;
    }

    public boolean annuler() {
        if (this.status == StatusEntretien.VALIDE) {
            return false;
        }

        this.status = StatusEntretien.ANNULE;

        return true;
    }

}
