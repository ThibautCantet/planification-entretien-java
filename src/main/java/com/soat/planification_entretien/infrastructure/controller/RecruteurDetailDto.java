package com.soat.planification_entretien.infrastructure.controller;

public record RecruteurDetailDto(
    Integer id,
    String email,
    String competence) {


    public RecruteurDetailDto(Integer id, String email, String language, Integer experienceInYears) {
        this(id, email, String.format("%s %s ans XP", language, experienceInYears));
    }
}
