package com.soat.planification_entretien.entretien.command.domain;

public record Candidat(
        Integer id,
        String adresseEmail,
        Profil profil) {

    public Candidat(
            Integer id,
            String language,
            String adresseEmail,
            int experienceInYears) {
        this(id, adresseEmail, new Profil(language, experienceInYears));
    }

    public String language() {
        return profil.language();
    }

    public Integer experienceInYears() {
        return profil.experienceInYears();
    }
}
