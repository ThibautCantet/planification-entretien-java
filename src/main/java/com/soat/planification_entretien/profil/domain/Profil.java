package com.soat.planification_entretien.profil.domain;

public record Profil(Integer experienceInYears, String language) {
    boolean estCompatibe(Profil profil) {
        return this.language.equals(profil.language) && this.experienceInYears >= profil.experienceInYears;
    }
}
