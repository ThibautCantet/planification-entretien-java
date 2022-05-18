package com.soat.recruteur.domain;

import java.util.UUID;

public class Recruteur {
    private final RecruteurId id;
    private final Language language;
    private final Email email;
    private final AnneesExperience anneesExperience;

    public Recruteur(UUID id, String language, String email, Integer anneesExperience) {
        this.id = new RecruteurId(id);
        this.language = new Language(language);
        this.email = new Email(email);
        this.anneesExperience = new AnneesExperience(anneesExperience);
    }

    public UUID getId() {
        return id.id();
    }

    public String getEmail() {
        return email.adresse();
    }

    public Integer getAnneesExperience() {
        return anneesExperience.nombre();
    }

    public String getLanguage() {
        return language.name();
    }
}
