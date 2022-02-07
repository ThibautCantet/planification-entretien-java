package com.soat.planification_entretien.model;

import java.time.LocalDateTime;

public record Entretien(LocalDateTime dateEtHeure, String emailCandidat, String emailRecruteur) {
}
