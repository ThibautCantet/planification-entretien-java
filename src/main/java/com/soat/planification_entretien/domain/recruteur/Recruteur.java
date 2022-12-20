package com.soat.planification_entretien.domain.recruteur;

public class Recruteur {
    private static final int MINIMUM_XP_REQUISE = 3;

    private Integer id;

    private String language;
    private RecruteurEmail email;
    private Integer experienceInYears;

    public Recruteur(String language, String email, int experienceInYears) {
        this(null, language, email, experienceInYears);
    }

    public Recruteur(Integer recruteurId, String language, String email, Integer experienceInYears) {
        if (language.isBlank() || experienceInYears < MINIMUM_XP_REQUISE) {
            throw new IllegalArgumentException();
        }
        this.id = recruteurId;
        this.language = language;
        this.email = new RecruteurEmail(email);
        this.experienceInYears = experienceInYears;
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

    public String getAdresseEmail() {
        return email.adresse();
    }

    public Integer getExperienceInYears() {
        return experienceInYears;
    }
}
