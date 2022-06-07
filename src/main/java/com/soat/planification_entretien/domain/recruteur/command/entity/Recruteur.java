package com.soat.planification_entretien.domain.recruteur.command.entity;

import java.util.ArrayList;
import java.util.List;

import com.soat.planification_entretien.domain.entretien.command.entity.RendezVous;

public class Recruteur {
    private Integer id;

    private String language;
    private String email;
    private Integer experienceInYears;

    private List<RendezVous> rendezVous;

    public Recruteur(String language, String email, int experienceInYears) {
        this.language = language;
        this.email = email;
        this.experienceInYears = experienceInYears;
        this.rendezVous = new ArrayList<>();
    }

    public Recruteur(int recruteurId, String language, String email, Integer experienceInYears, List<RendezVous> rendezVous) {
        id = recruteurId;
        this.language = language;
        this.email = email;
        this.experienceInYears = experienceInYears;
        this.rendezVous = rendezVous;
    }

    public static Recruteur of(Integer id, Recruteur recruteur) {
        recruteur.id = id;
        return recruteur;
    }

    public Integer getId() {
        return id;
    }

    public String getLanguage() {
        return language;
    }

    public String getEmail() {
        return email;
    }

    public Integer getExperienceInYears() {
        return experienceInYears;
    }

    public List<RendezVous> getRendezVous() {
        return rendezVous;
    }
}
