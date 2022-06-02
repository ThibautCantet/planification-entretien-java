package com.soat.planification_entretien.domain.entretien.listener.dto;

import java.time.LocalDateTime;

public record Entretien(String emailRecruteur, String emailCandidat, LocalDateTime horaire) {
}
