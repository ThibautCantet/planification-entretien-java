package com.soat.planification_entretien.recruteur.command.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.soat.planification_entretien.recruteur.command.domain.entity.Recruteur;

public interface RecruteurRepository {
    UUID next();

    Optional<Recruteur> findById(String recruteurId);

    Recruteur save(Recruteur recruteur);

    List<Recruteur> findAll();
}
