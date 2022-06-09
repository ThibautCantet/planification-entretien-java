package com.soat.planification_entretien.entretien.command.domain.entity;

record Profil(String language, Integer experienceInYears) {
    boolean estCompatible(Profil candidat) {
        return language.equals(candidat.language())
                && experienceInYears > candidat.experienceInYears();
    }
}