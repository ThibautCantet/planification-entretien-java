package com.soat.planification_entretien.application.controller;

import java.time.LocalDateTime;

import com.soat.planification_entretien.use_case.EntretienDetail;

public record EntretienDetailDto(int id, String emailCandidat, String emailRecruteur, String language,
                                 LocalDateTime horaire) implements EntretienDetail {
}
