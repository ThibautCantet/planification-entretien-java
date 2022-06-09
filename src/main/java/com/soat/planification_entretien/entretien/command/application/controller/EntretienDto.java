package com.soat.planification_entretien.entretien.command.application.controller;

import java.time.LocalDateTime;

public record EntretienDto(int candidatId, String recruteurId, LocalDateTime disponibiliteDuCandidat
) {
}
