package com.soat.planification_entretien.domain;

import java.time.LocalDateTime;

public interface IEntretien {
    int id();

    String emailCandidat();

    String emailRecruteur();

    String language();

    LocalDateTime horaire();
}
