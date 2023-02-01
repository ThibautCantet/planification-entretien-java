package com.soat.planification_entretien.domain.entretien;

public record Recruteur(
        Integer id,
        String adresseEmail,
        Profil profil) {

    public Recruteur(
            Integer id,
            String language,
            String adresseEmail,
            int experienceInYears) {
        this(id, adresseEmail, new Profil(language, experienceInYears));
    }

    public boolean estCompatible(Candidat candidat) {
        return this.profil.estCompatible(candidat.profil());
    }

    public String getLanguage() {
        return profil.language();
    }

    public int getExperienceInYears() {
        return profil.experienceInYears();
    }
}
