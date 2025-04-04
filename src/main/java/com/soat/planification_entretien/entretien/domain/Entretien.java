package com.soat.planification_entretien.entretien.domain;

import java.time.LocalDateTime;

import static com.soat.planification_entretien.entretien.domain.Status.*;

public class Entretien implements IEntretien {
    private EntretienId id;

    private Candidat prospect;

    private LocalDateTime horaireEntretien;

    private ConsultantRecruteur recruteur;
    private Status status;

    public Entretien(Integer id, Candidat prospect, ConsultantRecruteur recruteur, LocalDateTime horaire, Status status) {
        this.id = new EntretienId(id);
        this.prospect = prospect;
        this.recruteur = recruteur;
        this.horaireEntretien = horaire;
        this.status = status;
    }

    private Entretien(Candidat prospect, ConsultantRecruteur recruteur, LocalDateTime horaire) {
        this.prospect = prospect;
        this.recruteur = recruteur;
        this.horaireEntretien = horaire;
    }

    public Entretien(Candidat prospect, ConsultantRecruteur recruteur) {
        this.prospect = prospect;
        this.recruteur = recruteur;
    }

    public static Entretien of(Integer id, Candidat prospect, ConsultantRecruteur recruteur, LocalDateTime horaire, Status status) {
        return new Entretien(id, prospect, recruteur, horaire, status);
    }

    public static Entretien of(Candidat prospect, ConsultantRecruteur recruteur, LocalDateTime dateEtHeureDisponibiliteDuRecruteur) {
        return new Entretien(prospect, recruteur, dateEtHeureDisponibiliteDuRecruteur);
    }

    public static Entretien of(Integer newId, Entretien entretien) {
        entretien.id = new EntretienId(newId);
        return entretien;
    }

    public Candidat getCandidat() {
        return prospect;
    }

    public ConsultantRecruteur getRecruteur() {
        return recruteur;
    }

    public LocalDateTime getHoraireEntretien() {
        return horaireEntretien;
    }

    public Integer getId() {
        return id.value();
    }

    public boolean planifier(LocalDateTime dateEtHeureDisponibiliteDuCandidat, LocalDateTime dateEtHeureDisponibiliteDuRecruteur) {
        boolean planifiable = recruteur.estCompatible(prospect)
                              && dateEtHeureDisponibiliteDuCandidat.equals(dateEtHeureDisponibiliteDuRecruteur);

        if (planifiable) {
            horaireEntretien = dateEtHeureDisponibiliteDuCandidat;
            status = Status.PLANIFIE;
        }

        return planifiable;
    }

    @Override
    public String getEmailCandidat() {
        return prospect.email();
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

    public Status getStatus() {
        return status;
    }

    public void valider() {
        status = VALIDE;
    }

    @Override
    public String toString() {
        return "Entretien{" +
               "horaireEntretien=" + horaireEntretien +
               ", id=" + id +
               ", prospect=" + prospect +
               ", recruteur=" + recruteur +
               ", status=" + status +
               '}';
    }

    public void annuler() {
        this.status = ANNULE;
    }
}
