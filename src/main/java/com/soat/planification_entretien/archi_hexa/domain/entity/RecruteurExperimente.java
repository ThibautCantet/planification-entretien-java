package com.soat.planification_entretien.archi_hexa.domain.entity;

public record RecruteurExperimente(String email, String languageXP) {

    public RecruteurExperimente(String email, String language, Integer experienceInYears) {
        this(email, language + " " + experienceInYears + " ans XP");
    }
}
