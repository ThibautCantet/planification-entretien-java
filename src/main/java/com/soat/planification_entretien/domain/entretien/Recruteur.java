package com.soat.planification_entretien.domain.entretien;

public class Recruteur {
    private final Integer id;
    private final String adresseEmail;
    private final Profil profil;

    public Recruteur(
            Integer id,
            String language,
            String adresseEmail,
            int experienceInYears) {
        this.id = id;
        this.adresseEmail = adresseEmail;
        this.profil = new Profil(language, experienceInYears);
    }

    public Integer getId() {
        return id;
    }

    public String getAdresseEmail() {
        return adresseEmail;
    }

    public boolean estCompatible(Candidat candidat) {
        return this.profil.estCompatible(candidat.getProfil());
    }

    public String getLanguage() {
        return profil.language();
    }

    public int getExperienceInYears() {
        return profil.experienceInYears();
    }
}
