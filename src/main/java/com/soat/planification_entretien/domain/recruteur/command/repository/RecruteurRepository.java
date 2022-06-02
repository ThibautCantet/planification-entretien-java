package com.soat.planification_entretien.domain.recruteur.command.repository;

import java.util.Optional;

import com.soat.planification_entretien.domain.recruteur.command.entity.Recruteur;

public interface RecruteurRepository {
    Optional<Recruteur> findById(int recruteurId);

    Recruteur save(Recruteur recruteur);

}
