package com.soat.planification_entretien.domain.model;

public class Recruteur {
    private Integer id;

    private String language;
    private String email;
    private Integer experienceInYears;

    public Recruteur(String language, String email, int experienceInYears) {
        this.language = language;
        this.email = email;
        this.experienceInYears = experienceInYears;
    }

    public Recruteur(int recruteurId, String language, String email, Integer experienceInYears) {
        id = recruteurId;
        this.language = language;
        this.email = email;
        this.experienceInYears = experienceInYears;
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
}
