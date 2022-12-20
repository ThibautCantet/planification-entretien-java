package com.soat.planification_entretien.domain.entretien;

public record Profil(String language, int experienceInYears) {

    public boolean estCompatible(Profil candidat) {
        return this.language.equals(candidat.language)
                && this.experienceInYears > candidat.experienceInYears;
    }
}
