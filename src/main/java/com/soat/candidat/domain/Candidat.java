package com.soat.candidat.domain;

import java.util.UUID;

public class Candidat {
    private CandidatId id;
    private final String language;
    private final String email;
    private final int experienceInYears;

    public Candidat(UUID id, String language, String email, int experienceInYears) {
        this.id = new CandidatId(id);
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
