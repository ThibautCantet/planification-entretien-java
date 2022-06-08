package com.soat.planification_entretien.application.controller.command;

import java.time.LocalDateTime;

public record EntretienDto(int candidatId, String recruteurId, LocalDateTime disponibiliteDuCandidat,
                           LocalDateTime disponibiliteDuRecruteur) {
}
