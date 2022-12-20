package com.soat.planification_entretien.domain.entretien;

public record Candidat(
        Integer id,
        String language,
        String adresseEmail,
        int experienceInYears) {
}
