package com.soat.planification_entretien.domain.recruteur.command.entity;

public class Recruteur {
    private RecruteurId id;

    private Language language;
    private Email email;
    private AnneesExperience experienceInYears;

    private Recruteur(String recruteurId, String language, String email, Integer experienceInYears) {
        id = new RecruteurId(recruteurId);
        this.language = Language.create(language);
        this.email = Email.create(email);
        this.experienceInYears = AnneesExperience.create(experienceInYears);
    }

    public static Recruteur create(String recruteurId, String language, String email, Integer experienceInYears) {
        Recruteur recruteur = new Recruteur(recruteurId, language, email, experienceInYears);
        if (recruteur.id == null || !isValid(recruteur)) {
            return null;
        }
        return recruteur;
    }

    private static boolean isValid(Recruteur recruteur) {
        return recruteur.language != null && recruteur.email != null && recruteur.experienceInYears != null;
    }

    public static Recruteur of(String id, Recruteur recruteur) {
        recruteur.id = new RecruteurId(id);
        return recruteur;
    }

    public String getId() {
        return id.value();
    }

    public String getLanguage() {
        return language.nom();
    }

    public String getEmail() {
        return email.address();
    }

    public Integer getExperienceInYears() {
        return experienceInYears.nombre();
    }
}
