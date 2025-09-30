package com.soat.planification_entretien.application.use_case.output_port;

import java.util.Optional;

import com.soat.planification_entretien.domain.model.Recruteur;

public interface RecruteurPort {
    Optional<Recruteur> findById(int recruteurId);

    int save(Recruteur recruteur);
}
