package com.soat.planification_entretien.infrastructure.controller;

import java.time.LocalDateTime;

public record EntretienDto(java.util.UUID candidatId, java.util.UUID recruteurId, LocalDateTime disponibiliteDuCandidat,
                           LocalDateTime disponibiliteDuRecruteur) {
}
