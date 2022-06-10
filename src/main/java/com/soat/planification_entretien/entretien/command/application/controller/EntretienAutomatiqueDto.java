package com.soat.planification_entretien.entretien.command.application.controller;

import java.time.LocalDateTime;

public record EntretienAutomatiqueDto(int candidatId, LocalDateTime disponibiliteDuCandidat) {
}
