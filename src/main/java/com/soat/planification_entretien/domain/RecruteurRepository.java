package com.soat.planification_entretien.domain;

import java.util.Optional;

public interface RecruteurRepository {
    Recruteur save(Recruteur recruteur);

    Optional<Recruteur> findById(int recruteurId);
}
