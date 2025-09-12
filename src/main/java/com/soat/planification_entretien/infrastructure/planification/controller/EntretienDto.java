package com.soat.planification_entretien.infrastructure.planification.controller;

import java.time.LocalDateTime;

public record EntretienDto(int candidatId, LocalDateTime disponibiliteDuCandidat,
                           LocalDateTime disponibiliteDuRecruteur) {
}
