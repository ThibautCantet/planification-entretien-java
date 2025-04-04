package com.soat.planification_entretien.profil.infrastructure.controller;

public record RecruteurDetailDto(
        int id,
        String competence,
        String email, boolean disponible) {


    public RecruteurDetailDto(int id, String language, Integer experienceInYears, String email, boolean disponible) {
        this(id, String.format("%s %s ans XP", language, experienceInYears), email,
                disponible);
    }
}
