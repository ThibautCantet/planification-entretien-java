package com.soat.planification_entretien.rendez_vous.query.application.controller;

import java.time.LocalDateTime;

public record RendezVousDto(String emailCandidat, LocalDateTime horaire) {
}
