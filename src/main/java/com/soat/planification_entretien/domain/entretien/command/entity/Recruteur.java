package com.soat.planification_entretien.domain.entretien.command.entity;

public class Recruteur {

    private final Profil profil;
    private final String email;
    private final String id;

    public Recruteur(String id,
                     String language,
                     String email,
                     Integer experienceInYears) {
        this.id = id;
        this.profil = new Profil(language, experienceInYears);
        this.email = email;
    }

    public boolean isCompatible(Candidat candidat) {
        return profil.estCompatible(candidat.getProfil());
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getLanguage() {
        return profil.language();
    }

    public Integer getExperienceInYears() {
        return profil.experienceInYears();
    }
}
