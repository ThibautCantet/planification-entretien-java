package com.soat.planification_entretien.domain;

import java.util.UUID;

public class Candidat {
    private UUID id;
    private String language;
    private String email;
    private Integer experienceInYears;

    private Candidat(UUID id, String language, String email, int experienceInYears) {
        this.id = id;
        this.language = language;
        this.email = email;
        this.experienceInYears = experienceInYears;
    }

    public static Candidat of(UUID id, String language, String email, int experienceInYears) {
        return new Candidat(id, language, email, experienceInYears);
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
}
