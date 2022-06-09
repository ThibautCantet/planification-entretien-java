package com.soat.planification_entretien.entretien.query.dto;

import java.time.LocalDateTime;

public interface IEntretien {
    String getId();

    String getEmailCandidat();

    String getEmailRecruteur();

    String getLanguage();

    LocalDateTime getHoraire();

    String getStatus();
}
