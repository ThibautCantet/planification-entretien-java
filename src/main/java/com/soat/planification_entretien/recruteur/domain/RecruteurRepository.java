package com.soat.planification_entretien.recruteur.domain;

import java.util.List;
import java.util.Optional;

import com.soat.planification_entretien.entretien.domain.aggregate.RecruteurPlanifié;

public interface RecruteurRepository {

    Optional<Recruteur> findById(int recruteurId);

    Recruteur save(Recruteur recruteur);

    List<Recruteur> find10AnsExperience();

    Recruteur findByEmail(String email);

    List<Recruteur> findAll();
}
