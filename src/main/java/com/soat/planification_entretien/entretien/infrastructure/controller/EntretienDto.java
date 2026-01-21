package com.soat.planification_entretien.entretien.infrastructure.controller;

import java.time.LocalDateTime;

public record EntretienDto(int candidatId, LocalDateTime disponibiliteDuCandidat) {
}
