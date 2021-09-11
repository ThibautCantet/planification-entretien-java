package com.soat.candidat.domain;

import java.util.UUID;

public class Candidat {
    private CandidatId id;
    private final Language language;
    private final Email email;
    private final AnneeExperience experienceInYears;

    public Candidat(UUID id, String language, String email, Integer experienceInYears) {
        this.id = new CandidatId(id);
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
