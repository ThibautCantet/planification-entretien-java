package com.soat.planification_entretien.domain;

public record RecruteurDetail(
    Integer id,
    String email,
    String competence) {


    public RecruteurDetail(Integer id, String email, String language, Integer experienceInYears) {
        this(id, email, String.format("%s %s ans XP", language, experienceInYears));
    }
}
