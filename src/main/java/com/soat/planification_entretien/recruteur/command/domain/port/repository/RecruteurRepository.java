package com.soat.planification_entretien.recruteur.command.domain.port.repository;

import java.util.Optional;

import com.soat.planification_entretien.recruteur.command.domain.model.Recruteur;

public interface RecruteurRepository {
    Optional<Recruteur> findById(int recruteurId);

    Recruteur save(Recruteur recruteur);

    Optional<Recruteur> findByEmail(String email);
}
