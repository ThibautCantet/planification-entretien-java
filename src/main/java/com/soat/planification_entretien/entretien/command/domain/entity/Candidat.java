package com.soat.planification_entretien.entretien.command.domain.entity;

import java.util.UUID;

public class Candidat {
    private UUID id;
    private final Profil profil;

    private String email;

    public Candidat(UUID candidatId, String language, String email, Integer experienceInYears) {
        this.id = candidatId;
        this.profil = new Profil(language, experienceInYears);
        this.email = email;
    }

    public static Candidat of(UUID id, Candidat candidat) {
        candidat.id = id;
        return candidat;
    }

    public UUID getId() {
        return id;
    }

    public Profil getProfil() {
        return profil;
    }

    public String getLanguage() {
        return profil.language();
    }

    public Integer getExperienceInYears() {
        return profil.experienceInYears();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
