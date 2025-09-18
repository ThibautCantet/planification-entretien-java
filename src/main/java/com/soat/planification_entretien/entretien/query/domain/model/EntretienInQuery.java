package com.soat.planification_entretien.entretien.query.domain.model;

import java.time.LocalDateTime;

public record EntretienInQuery(Integer id, String emailCandidat, String emailRecruteur, String language, LocalDateTime horaire,
                               String status) {

}
