package com.soat.planification_entretien.entretien.command.domain.entity;

import java.time.LocalDateTime;

import com.soat.planification_entretien.entretien.command.application.controller.Status;
import com.soat.planification_entretien.entretien.query.dto.IEntretien;

public class Entretien implements IEntretien {
    private Integer id;

    private Candidat candidat;

    private Creneau creneau;

    private Recruteur recruteur;
    private Status status;

    public Entretien(Integer id, Candidat candidat, Recruteur recruteur, LocalDateTime horaire) {
        this.id = id;
        this.candidat = candidat;
        this.recruteur = recruteur;
        this.creneau = new Creneau(horaire);
        this.status = Status.PLANIFIE;
    }

    private Entretien(Candidat candidat, Recruteur recruteur, LocalDateTime horaire) {
        this.candidat = candidat;
        this.recruteur = recruteur;
        this.creneau = new Creneau(horaire);
        this.status = Status.PLANIFIE;
    }

    public Entretien(Candidat candidat, Recruteur recruteur) {
        this.candidat = candidat;
        this.recruteur = recruteur;
        this.status = Status.PLANIFIE;
    }

    public static Entretien of(Integer id, Candidat candidat, Recruteur recruteur, LocalDateTime horaire) {
        return new Entretien(id, candidat, recruteur, horaire);
    }

    public static Entretien of(Candidat candidat, Recruteur recruteur, LocalDateTime horaire) {
        return new Entretien(candidat, recruteur, horaire);
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
        return creneau.debut();
    }

    public Integer getId() {
        return id;
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

    @Override
    public String getStatus() {
        return status.name();
    }

    public int getStatusValue() {
        return status.getValue();
    }
}