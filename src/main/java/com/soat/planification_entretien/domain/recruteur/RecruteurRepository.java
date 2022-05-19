package com.soat.planification_entretien.domain.recruteur;

import java.util.Optional;

import com.soat.planification_entretien.domain.recruteur.Recruteur;

public interface RecruteurRepository {
    Optional<Recruteur> findById(int recruteurId);

    Recruteur save(Recruteur recruteur);
}
