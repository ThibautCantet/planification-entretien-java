package com.soat.planification_entretien.infrastructure.controller;

import java.time.LocalDateTime;

import com.soat.planification_entretien.domain.IEntretien;

public record EntretienDetailDto(int id, String emailCandidat, String emailRecruteur, String language,
                                 LocalDateTime horaire) implements IEntretien {
}
