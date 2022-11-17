package com.soat.planification_entretien.archi_hexa.domain.entity;

import java.time.LocalDateTime;

public record EntretienDetail(int id, String emailCandidat, String emailRecruteur, String language,
                              LocalDateTime horaire) {
}
