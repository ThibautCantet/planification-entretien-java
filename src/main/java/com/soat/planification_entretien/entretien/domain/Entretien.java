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

    public Candidat getCandidat() {
        return candidat;
    }

    public RecruteurPlanifié getRecruteur() {
        return recruteur;
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
}
