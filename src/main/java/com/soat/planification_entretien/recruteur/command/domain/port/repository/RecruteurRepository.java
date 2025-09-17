package com.soat.planification_entretien.recruteur.command.domain.port.repository;

import java.util.List;
import java.util.Optional;

import com.soat.planification_entretien.recruteur.command.domain.model.Recruteur;

public interface RecruteurRepository {
    Optional<Recruteur> findById(int recruteurId);

    Recruteur save(Recruteur recruteur);

    List<Recruteur> find10AnsExperience();

    Optional<Recruteur> findByEmail(String email);
}
