package com.soat.planification_entretien.domain.entretien.command.entity;

import java.time.LocalDateTime;

public record RendezVous(String emailCandidat, LocalDateTime horaire) {
}
