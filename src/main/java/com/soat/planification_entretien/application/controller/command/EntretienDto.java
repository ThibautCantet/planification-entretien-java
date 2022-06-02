package com.soat.planification_entretien.application.controller.command;

import java.time.LocalDateTime;

public record EntretienDto(int candidatId, int recruteurId, LocalDateTime disponibiliteDuCandidat,
                           LocalDateTime disponibiliteDuRecruteur) {
}
