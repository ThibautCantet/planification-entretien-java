package com.soat.planification_entretien.entretien.command.application.controller;

import java.time.LocalDateTime;
import java.util.UUID;

public record EntretienAutomatiqueDto(UUID candidatId, LocalDateTime disponibiliteDuCandidat) {
}
