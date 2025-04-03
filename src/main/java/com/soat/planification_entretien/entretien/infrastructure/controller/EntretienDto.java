package com.soat.planification_entretien.entretien.infrastructure.controller;

import java.time.LocalDateTime;
import java.util.UUID;

public record EntretienDto(UUID candidatId, int recruteurId, LocalDateTime disponibiliteDuCandidat,
                           LocalDateTime disponibiliteDuRecruteur) {
}
