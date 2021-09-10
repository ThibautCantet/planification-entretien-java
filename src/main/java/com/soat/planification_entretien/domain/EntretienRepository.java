package com.soat.planification_entretien.domain;

import java.util.UUID;

public interface EntretienRepository {
    UUID next();

    Entretien findById(UUID entretienId);

    void save(Entretien entretien);
}
