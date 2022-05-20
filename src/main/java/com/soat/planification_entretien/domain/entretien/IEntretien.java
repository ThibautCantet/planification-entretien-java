package com.soat.planification_entretien.domain.entretien;

import java.time.LocalDateTime;

public interface IEntretien {
    Integer getId();

    String getEmailCandidat();

    String getEmailRecruteur();

    String getLanguage();

    LocalDateTime getHoraire();
}
