package com.soat.planification_entretien.use_case;

import java.time.LocalDateTime;

public interface EntretienDetail {
    int id();

    String emailCandidat();

    String emailRecruteur();

    String language();

    LocalDateTime horaire();
}
