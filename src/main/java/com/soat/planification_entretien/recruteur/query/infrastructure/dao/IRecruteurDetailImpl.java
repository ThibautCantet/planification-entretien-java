package com.soat.planification_entretien.recruteur.query.infrastructure.dao;

import com.soat.planification_entretien.recruteur.query.application.IRecruteurDetail;

public record IRecruteurDetailImpl(
        int id,
        String competence,
        String email) implements IRecruteurDetail {


    public IRecruteurDetailImpl(int id, String language, Integer experienceInYears, String email) {
        this(id, String.format("%s %s ans XP", language, experienceInYears), email);
    }
}
