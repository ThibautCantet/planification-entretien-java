package com.soat.planification_entretien.use_case;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record PlanifierEntretienCommand(UUID candidatId, LocalDateTime disponibiliteDuCandidat, UUID recruteurId, LocalDate dateDeDisponibiliteDuRecruteur) {
}
