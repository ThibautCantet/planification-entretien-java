package com.soat.planification_entretien.candidat.command.domain.entity;

import java.util.UUID;

public class Candidat {
    private UUID id;
    private String language;
    private String email;
    private Integer experienceInYears;

    public Candidat(UUID candidatId, String language, String email, Integer experienceInYears) {
        this.id = candidatId;
        this.language = language;
        this.email = email;
        this.experienceInYears = experienceInYears;
    }

    public UUID getId() {
        return id;
    }

    public String getLanguage() {
        return language;
    }

    public String getEmail() {
        return email;
    }

    public Integer getExperienceInYears() {
        return experienceInYears;
    }
}
