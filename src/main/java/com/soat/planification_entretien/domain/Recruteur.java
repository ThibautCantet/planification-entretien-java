package com.soat.planification_entretien.domain;

import java.util.UUID;

public class Recruteur {
    private UUID id;
    private String language;
    private String email;
    private Integer experienceInYears;

    private Recruteur(UUID id, String language, String email, int experienceInYears) {
        this.id = id;
        this.language = language;
        this.email = email;
        this.experienceInYears = experienceInYears;
    }

    public static Recruteur of(UUID id, String language, String email, Integer experienceInYears) {
        return new Recruteur(id, language, email, experienceInYears);
    }

    public UUID getId() {
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

    public boolean peutEvaluer(Candidat candidat) {
        return this.language.equals(candidat.getLanguage())
                && this.experienceInYears > candidat.getExperienceInYears();
    }
}
