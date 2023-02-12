package com.soat.planification_entretien.recruteur.query.infrastructure.controller;

import com.soat.planification_entretien.recruteur.query.application.IRecruteurDetail;

public record RecruteurDetailDto(
        int id,
        String competence,
        String email) implements IRecruteurDetail {
}
