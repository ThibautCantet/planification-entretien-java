package com.soat.planification_entretien.rendez_vous.command.domain.entity;

import java.time.LocalDateTime;

public record RendezVous(String emailCandidat, LocalDateTime horaire) {
}
