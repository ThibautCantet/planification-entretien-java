package com.soat.planification_entretien.domain.entretien.command.entity;

public record Candidat(
        Integer id,
        String language,
        String email,
        Integer experienceInYears) {
}
