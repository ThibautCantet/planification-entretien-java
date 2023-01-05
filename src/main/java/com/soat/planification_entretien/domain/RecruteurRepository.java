package com.soat.planification_entretien.domain;

import java.util.List;
import java.util.Optional;

public interface RecruteurRepository {
    Optional<Recruteur> findById(int recruteurId);

    Recruteur save(Recruteur recruteur);

    List<Recruteur> find10AnsExperience();
}
