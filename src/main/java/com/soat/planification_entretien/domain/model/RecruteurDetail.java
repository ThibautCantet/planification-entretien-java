package com.soat.planification_entretien.domain.model;

public record RecruteurDetail(
    Integer id,
    String email,
    String competence) {

    public RecruteurDetail(Integer id, String email, String competence, Integer experienceInYears) {
        this(id, email, String.format("%s %d ans XP", competence, experienceInYears));
    }
}
