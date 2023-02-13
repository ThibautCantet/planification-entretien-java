package com.soat.planification_entretien.recruteur.query.application;

public record RecruteurDetail(
        int id,
        String competence,
        String email) {

    public RecruteurDetail(int id, String language, Integer experienceInYears, String email) {
        this(id, String.format("%s %s ans XP", language, experienceInYears), email);
    }
}
