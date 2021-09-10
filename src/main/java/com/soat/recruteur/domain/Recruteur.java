package com.soat.recruteur.domain;

import java.util.UUID;

public class Recruteur {
    private final RecruteurId id;
    private final String language;
    private final String email;
    private final Integer experienceInYears;

    public Recruteur(UUID id, String language, String email, Integer experienceInYears) {
        this.id = new RecruteurId(id);
        this.language = language;
        this.email = email;
        this.experienceInYears = experienceInYears;
    }

    public UUID getId() {
        return id.id();
    }

    public String getEmail() {
        return email;
    }
}
