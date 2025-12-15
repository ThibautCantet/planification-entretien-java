package com.soat.planification_entretien.domain;

import java.util.Optional;

public interface RecruteurPort {
    Optional<Recruteur> findById(int recruteurId);

    Recruteur save(Recruteur recruteur);
}
