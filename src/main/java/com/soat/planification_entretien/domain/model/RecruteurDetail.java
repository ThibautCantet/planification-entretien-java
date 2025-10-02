package com.soat.planification_entretien.domain.model;

public record RecruteurDetail(
    Integer id,
    String email,
    String competence) {

    public RecruteurDetail(Integer id, String email, String language, Integer xp) {
        this(id, email, String.format("%s %s ans XP", language, xp));
    }
}
