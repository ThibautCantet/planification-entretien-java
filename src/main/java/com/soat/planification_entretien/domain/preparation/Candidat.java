package com.soat.planification_entretien.domain.preparation;

public class Candidat {

    private Integer id;

    private String language;
    private String email;
    private Integer experienceInYears;

    public Candidat(String language, String email, int experienceInYears) {
        this(null, language, email, experienceInYears);
    }

    public Candidat(Integer candidatId, String language, String email, Integer experienceInYears) {
        this.id = candidatId;
        this.language = language;
        this.email = Email.of(email).adresse();
        this.experienceInYears = experienceInYears;
    }

    public static Candidat of(Integer id, Candidat candidat) {
        candidat.id = id;
        return candidat;
    }

    public static Candidat create(String language, String email, int anneesExperience) {
        if (language.isBlank() || anneesExperience < 0) {
            throw new IllegalArgumentException();
        }
        return new Candidat(language, email, anneesExperience);
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
