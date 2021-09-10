package com.soat.planification_entretien.event;

import java.time.LocalDateTime;
import java.util.UUID;

public record EntretienEchouee(UUID id, UUID candidatId, UUID recruteurId, LocalDateTime horaireEntretien) implements ResultatPlanificationEntretien {
}
