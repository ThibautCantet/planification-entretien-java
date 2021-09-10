package com.soat.planification_entretien.event;

import java.time.LocalDateTime;
import java.util.UUID;

public record EntretienPlanifie(UUID id, UUID candidat, UUID recruteur, LocalDateTime horaireEntretien) implements ResultatPlanificationEntretien {
}
