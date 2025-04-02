package com.soat.planification_entretien.entretien.domain;

import java.time.LocalDateTime;

import com.soat.planification_entretien.profil.domain.Candidat;
import com.soat.planification_entretien.profil.domain.Recruteur;

public class Entretien implements IEntretien {
    private EntretienId id;

    private Candidat candidat;

    private LocalDateTime horaireEntretien;

    private Recruteur recruteur;

    public Entretien(Integer id, Candidat candidat, Recruteur recruteur, LocalDateTime horaire) {
        this.id = new EntretienId(id);
        this.candidat = candidat;
        this.recruteur = recruteur;
        this.horaireEntretien = horaire;
    }

    private Entretien(Candidat candidat, Recruteur recruteur, LocalDateTime horaire) {
        this.candidat = candidat;
        this.recruteur = recruteur;
        this.horaireEntretien = horaire;
    }

    public Entretien(Candidat candidat, Recruteur recruteur) {
        this.candidat = candidat;
        this.recruteur = recruteur;
    }

    public static Entretien of(Integer id, Candidat candidat, Recruteur recruteur, LocalDateTime horaire) {
        return new Entretien(id, candidat, recruteur, horaire);
    }

    public static Entretien of(Candidat candidat, Recruteur recruteur, LocalDateTime dateEtHeureDisponibiliteDuRecruteur) {
        return new Entretien(candidat, recruteur, dateEtHeureDisponibiliteDuRecruteur);
    }

    public static Entretien of(Integer newId, Entretien entretien) {
        entretien.id = new EntretienId(newId);
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
        return id.value();
    }

    public boolean planifier(LocalDateTime dateEtHeureDisponibiliteDuCandidat, LocalDateTime dateEtHeureDisponibiliteDuRecruteur) {
        boolean planifiable = recruteur.estCompatible(candidat)
                && dateEtHeureDisponibiliteDuCandidat.equals(dateEtHeureDisponibiliteDuRecruteur);

        if (planifiable) {
            horaireEntretien = dateEtHeureDisponibiliteDuCandidat;
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
        return horaireEntretien;
    }
}
