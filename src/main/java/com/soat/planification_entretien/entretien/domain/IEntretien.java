package com.soat.planification_entretien.entretien.domain;

import java.time.LocalDateTime;

public interface IEntretien {
    Integer getId();

    String getEmailCandidat();

    String getEmailRecruteur();

    String getLanguage();

    LocalDateTime getHoraire();

    String getStatus();
}
