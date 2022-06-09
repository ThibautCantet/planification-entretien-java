package com.soat.planification_entretien.entretien.command.domain.entity;

import java.time.LocalDateTime;

import com.soat.planification_entretien.entretien.command.application.controller.Status;
import com.soat.planification_entretien.entretien.query.dto.IEntretien;

public class Entretien implements IEntretien {
    private EntretienId id;

    private Candidat candidat;

    private Creneau creneau;

    private Recruteur recruteur;
    private Status status;

    public Entretien(String id, Candidat candidat, Recruteur recruteur, LocalDateTime horaire, Status status) {
        this.id = new EntretienId(id);
        this.candidat = candidat;
        this.recruteur = recruteur;
        this.creneau = new Creneau(horaire);
        this.status = status;
    }

    public Entretien(String id, Candidat candidat, Recruteur recruteur, LocalDateTime horaire) {
        this(id, candidat, recruteur, horaire, Status.PLANIFIE);
    }

    private Entretien(Candidat candidat, Recruteur recruteur, LocalDateTime horaire) {
        this(null, candidat, recruteur, horaire);
    }

    public Entretien(String id, Candidat candidat, Recruteur recruteur) {
        this.id = new EntretienId(id);
        this.candidat = candidat;
        this.recruteur = recruteur;
        this.status = Status.PLANIFIE;
    }

    public static Entretien of(String id, Candidat candidat, Recruteur recruteur, LocalDateTime horaire, Status status) {
        return new Entretien(id, candidat, recruteur, horaire, status);
    }

    public static Entretien of(String id, Candidat candidat, Recruteur recruteur, LocalDateTime horaire) {
        return new Entretien(id, candidat, recruteur, horaire);
    }

    public static Entretien of(Candidat candidat, Recruteur recruteur, LocalDateTime horaire) {
        return new Entretien(candidat, recruteur, horaire);
    }

    public static Entretien of(String newId, Entretien entretien) {
        entretien.id = new EntretienId(newId);
        return entretien;
    }

    public Candidat getCandidat() {
        return candidat;
    }

    public Recruteur getRecruteur() {
        return recruteur;
    }

    @Override
    public String getId() {
        return id.value();
    }

    public boolean planifier(LocalDateTime dateEtHeureDisponibiliteDuCandidat) {
        boolean planifiable = recruteur.isCompatible(candidat)
                && recruteur.estDisponible(dateEtHeureDisponibiliteDuCandidat);

        if (planifiable) {
            creneau = new Creneau(dateEtHeureDisponibiliteDuCandidat);
        }

        return planifiable;
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
        return creneau.debut();
    }

    public void confirmer() {
        this.status = Status.CONFIRME;
    }

    public void annuler() {
        this.status = Status.ANNULE;
    }

    @Override
    public String getStatus() {
        return status.name();
    }

    public int getStatusValue() {
        return status.getValue();
    }
}
