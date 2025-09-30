package com.soat.planification_entretien.application.controller;

import java.time.LocalDateTime;

public record EntretienDetailDto(int id, String emailCandidat, String emailRecruteur, String language,
                                 LocalDateTime horaire) {
}
