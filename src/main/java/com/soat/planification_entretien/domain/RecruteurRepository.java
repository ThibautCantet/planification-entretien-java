package com.soat.planification_entretien.domain;

import java.util.Optional;
import java.util.UUID;


public interface RecruteurRepository {
    Recruteur save(Recruteur recruteur);

    Optional<Recruteur> findById(UUID recruteurId);
}
