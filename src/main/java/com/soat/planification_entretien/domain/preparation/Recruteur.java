package com.soat.planification_entretien.domain.preparation;

public class Recruteur {

    private static final int MINIMUM_XP_REQUISE = 3;

    private Integer id;

    private String language;
    private String email;
    private Integer experienceInYears;

    public Recruteur(String language, String email, int experienceInYears) {
        this(null, language, email, experienceInYears);
    }

    public Recruteur(Integer recruteurId, String language, String email, Integer experienceInYears) {
        this.id = new RecruteurId(recruteurId).value();
        this.language = language;
        this.email = email;
        this.experienceInYears = experienceInYears;
    }

    public static Recruteur of(Integer id, Recruteur recruteur) {
        recruteur.id = id;
        return recruteur;
    }

    public static Recruteur of(String language, String email, int anneesExperience) {
        if (language.isBlank() || anneesExperience < MINIMUM_XP_REQUISE) {
            throw new IllegalArgumentException();
        }

        return new Recruteur(language, Email.of(email).adresse(), anneesExperience);
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
