package com.soat.planification_entretien.domain.recruteur;

public class Recruteur {

    private Integer id;

    private Langage language;
    private RecruteurEmail email;
    private Experience experience;

    public Recruteur(String language, String email, int experienceInYears) {
        this(null, language, email, experienceInYears);
    }

    public Recruteur(Integer recruteurId, String language, String email, Integer experienceInYears) {
        this.id = recruteurId;
        this.language = new Langage(language);
        this.email = new RecruteurEmail(email);
        this.experience = new Experience(experienceInYears);
    }

    public static Recruteur of(Integer id, Recruteur recruteur) {
        recruteur.id = id;
        return recruteur;
    }

    public Integer getId() {
        return id;
    }

    public String getLanguage() {
        return language.nom();
    }

    public String getAdresseEmail() {
        return email.adresse();
    }

    public Integer getExperienceInYears() {
        return experience.annee();
    }
}
