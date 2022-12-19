package com.soat.planification_entretien.infrastructure.controller;

public record RecruteurDetailDto(
        int id,
        String competence,
        String email) {


    public RecruteurDetailDto(int id, String language, Integer experienceInYears, String email) {
        this(id, String.format("%s %s ans XP", language, experienceInYears), email);
    }
}
