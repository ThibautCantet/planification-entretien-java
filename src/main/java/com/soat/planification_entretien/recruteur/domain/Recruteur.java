package com.soat.planification_entretien.recruteur.domain;

public class Recruteur {

    private Integer id;

    private final CompetenceRecruteur competence;
    private final EmailRecruteur email;

    public Recruteur(String language, String email, int experienceInYears) {
        this(null, language, email, experienceInYears);
    }

    public Recruteur(Integer recruteurId, String language, String email, Integer experienceInYears) {
        this.id = recruteurId;
        this.competence = CompetenceRecruteur.of(language, experienceInYears);
        this.email = EmailRecruteur.of(email);
    }

    public static Recruteur of(Integer id, Recruteur recruteur) {
        recruteur.id = id;
        return recruteur;
    }

    public Integer getId() {
        return id;
    }

    public String getLanguage() {
        return competence.langage();
    }

    public String getEmail() {
        return email.adresse();
    }

    public Integer getExperienceInYears() {
        return competence.experienceEnAnnees();
    }
}
