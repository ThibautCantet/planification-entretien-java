package com.soat.recruteur.domain;

import java.util.UUID;

public class Recruteur {
    private final RecruteurId id;
    private final Language language;
    private final Email email;
    private final AnneesExperience experienceEnAnnees;

    public Recruteur(UUID id, String language, String email, Integer experienceEnAnnees) {
        this.id = new RecruteurId(id);
        this.language = new Language(language);
        this.email = new Email(email);
        this.experienceEnAnnees = new AnneesExperience(experienceEnAnnees);
    }

    public UUID getId() {
        return id.id();
    }

    public String getEmail() {
        return email.adresse();
    }

    public Integer getExperienceEnAnnees() {
        return experienceEnAnnees.nombre();
    }

    public String getLanguage() {
        return language.name();
    }
}
