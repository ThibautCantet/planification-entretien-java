package com.soat.planification_entretien.entretien.domain;

import java.util.UUID;

public record Candidat(UUID id, String language, String email, int experienceInYears) {
    Profil profil() {
        return new Profil(experienceInYears, language);
    }
}
