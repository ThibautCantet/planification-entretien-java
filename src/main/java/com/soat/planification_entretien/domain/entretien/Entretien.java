package com.soat.planification_entretien.domain.entretien;

import java.time.LocalDateTime;

import com.soat.planification_entretien.domain.Event;

public class Entretien implements IEntretien {
    private Integer id;

    private Candidat candidat;

    private LocalDateTime horaireEntretien;

    private Recruteur recruteur;
    private Status status = Status.PLANIFIE;

    public Entretien(Integer id, Candidat candidat, Recruteur recruteur, LocalDateTime horaire, Status status) {
        this.id = id;
        this.candidat = candidat;
        this.recruteur = recruteur;
        this.horaireEntretien = horaire;
        this.status = status;
    }

    private Entretien(Candidat candidat, Recruteur recruteur, LocalDateTime horaire, Status status) {
        this(null, candidat, recruteur, horaire, status);
    }

    public Entretien(Candidat candidat, Recruteur recruteur) {
        this.candidat = candidat;
        this.recruteur = recruteur;
    }

    public static Entretien of(Integer id, Candidat candidat, Recruteur recruteur, LocalDateTime horaire, Status status) {
        return new Entretien(id, candidat, recruteur, horaire, status);
    }

    public static Entretien of(Candidat candidat, Recruteur recruteur, LocalDateTime dateEtHeureDisponibiliteDuRecruteur, Status status) {
        return new Entretien(candidat, recruteur, dateEtHeureDisponibiliteDuRecruteur, status);
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

    public Event planifier(LocalDateTime dateEtHeureDisponibiliteDuCandidat, LocalDateTime dateEtHeureDisponibiliteDuRecruteur) {
        boolean planifiable = recruteur.estCompatible(candidat)
                && dateEtHeureDisponibiliteDuCandidat.equals(dateEtHeureDisponibiliteDuRecruteur);

        if (planifiable) {
            horaireEntretien = dateEtHeureDisponibiliteDuCandidat;
            return new EntretienCréé(id, recruteur.getId());
        }

        return new EntretienNonCréé();
    }

    public void valider() {
        status = Status.VALIDE;
    }

    @Override
    public String getEmailCandidat() {
        return candidat.getAdresseEmail();
    }

    @Override
    public String getEmailRecruteur() {
        return recruteur.getAdresseEmail();
    }

    @Override
    public String getLanguage() {
        return recruteur.getLanguage();
    }

    @Override
    public LocalDateTime getHoraire() {
        return horaireEntretien;
    }

    public String getStatus() {
        return status.name();
    }

    public int getStatusValue() {
        return status.getValue();
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
