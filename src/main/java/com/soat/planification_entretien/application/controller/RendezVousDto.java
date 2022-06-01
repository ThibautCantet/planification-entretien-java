package com.soat.planification_entretien.application.controller;

import java.time.LocalDateTime;

public record RendezVousDto(String emailCandidat, LocalDateTime horaire) {
}
