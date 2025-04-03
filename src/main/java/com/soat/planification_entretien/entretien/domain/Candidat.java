package com.soat.planification_entretien.entretien.domain;

public record Candidat(int id, String language, String email, int experienceInYears) {
    Profil profil() {
        return new Profil(experienceInYears, language);
    }
}
