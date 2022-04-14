package com.soat.planification_entretien.use_case;

import java.util.Optional;

import com.soat.planification_entretien.domain.Recruteur;

public interface RecruteurRepository {
    Recruteur save(Recruteur recruteur);

    Optional<Recruteur> findById(int recruteurId);
}
