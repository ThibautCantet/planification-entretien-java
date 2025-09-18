package com.soat.planification_entretien.entretien.query.infrastructure.controller;

import java.time.LocalDateTime;

public record EntretienDetailDto(
        Integer id,
        String emailCandidat,
        String emailRecruteur,
        String language,
        LocalDateTime horaire,
        String status) {

}
