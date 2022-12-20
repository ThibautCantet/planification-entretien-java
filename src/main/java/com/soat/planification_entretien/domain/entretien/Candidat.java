package com.soat.planification_entretien.domain.entretien;

public class Candidat {
    private final Integer id;
    private final String adresseEmail;
    private final Profil profil;

    public Candidat(
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

    public int getExperienceInYears() {
        return profil.experienceInYears();
    }

    Profil getProfil() {
        return profil;
    }

    public String getLanguage() {
        return profil.language();
    }
}
