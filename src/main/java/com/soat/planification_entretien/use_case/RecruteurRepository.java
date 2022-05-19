package com.soat.planification_entretien.use_case;

import java.util.Optional;

import com.soat.planification_entretien.domain.model.Recruteur;

public interface RecruteurRepository {
    Optional<Recruteur> findById(int recruteurId);

    Recruteur save(Recruteur recruteur);
}
