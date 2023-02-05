package com.soat.planification_entretien.recruteur.command.domain;

public class Recruteur {

    private Integer id;

    private Langage language;
    private RecruteurEmail email;
    private Experience experience;
    private Boolean disponible = true;

    public Recruteur(String language, String email, int experienceInYears) {
        this(null, language, email, experienceInYears);
    }

    public Recruteur(Integer recruteurId, String language, String email, Integer experienceInYears) {
        this(recruteurId,
                language,
                email,
                experienceInYears,
                true);
    }

    public Recruteur(Integer id, String language, String email, Integer experienceInYears, Boolean disponible) {
        if (language.isBlank()) {
            throw new IllegalArgumentException();
        }
        this.id = id;
        this.language = new Langage(language);
        this.email = new RecruteurEmail(email);
        this.experience = new Experience(experienceInYears);
        this.disponible = disponible;
    }

    public static Recruteur of(Integer id, Recruteur recruteur) {
        recruteur.id = id;
        return recruteur;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public boolean estDisponible() {
        return disponible;
    }

    public void rendreIndisponible() {
        disponible = false;
    }
}
