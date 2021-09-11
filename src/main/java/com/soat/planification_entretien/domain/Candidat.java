package com.soat.planification_entretien.domain;

import java.util.UUID;

public record Candidat(UUID id, String language, String email, Integer experienceEnAnnees) {
}
