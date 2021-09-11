package com.soat.recruteur.domain;

import java.util.UUID;

public class Recruteur {
    private final RecruteurId id;
    private final Language language;
    private final Email email;
    private final AnneeExperience experienceInYears;

    public Recruteur(UUID id, String language, String email, Integer experienceInYears) {
        this.id = new RecruteurId(id);
        this.language = new Language(language);
        this.email = new Email(email);
        this.experienceInYears = new AnneeExperience(experienceInYears);
    }

    public UUID getId() {
        return id.id();
    }

    public String getEmail() {
        return email.adresse();
    }
}
