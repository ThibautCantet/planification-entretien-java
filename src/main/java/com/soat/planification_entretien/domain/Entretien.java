package com.soat.planification_entretien.domain;

import java.time.LocalDateTime;

public class Entretien implements IEntretien {
    private Integer id;
    private Candidat candidat;
    private LocalDateTime horaireEntretien;
    private Recruteur recruteur;

    private Entretien(Integer id, Candidat candidat, Recruteur recruteur, LocalDateTime horaire) {
        this.id = id;
        this.candidat = candidat;
        this.recruteur = recruteur;
        this.horaireEntretien = horaire;
    }

    public Entretien() {
    }

    public static Entretien of(Candidat candidat, Recruteur recruteur) {
        return new Entretien(null, candidat, recruteur, null);
    }

    public static Entretien of(Integer id, Candidat candidat, Recruteur recruteur, LocalDateTime horaireEntretien) {
        return new Entretien(id, candidat, recruteur, horaireEntretien);
    }

    public Boolean planifier(LocalDateTime disponibiliteDuCandidat, LocalDateTime disponibiliteDuRecruteur) {
        return recruteur.getLanguage().equals(candidat.getLanguage())
               && recruteur.getExperienceInYears() > candidat.getExperienceInYears()
               && disponibiliteDuCandidat.equals(disponibiliteDuRecruteur);
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

    @Override
    public int id() {
        return id;
    }

    @Override
    public String emailCandidat() {
        return candidat.getEmail();
    }

    @Override
    public String emailRecruteur() {
        return recruteur.getEmail();
    }

    @Override
    public String language() {
        return candidat.getLanguage();
    }

    @Override
    public LocalDateTime horaire() {
        return horaireEntretien;
    }
}
