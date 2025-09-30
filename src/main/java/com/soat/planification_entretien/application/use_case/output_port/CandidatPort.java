package com.soat.planification_entretien.application.use_case.output_port;

import java.util.Optional;

import com.soat.planification_entretien.domain.model.Candidat;

public interface CandidatPort {
    Optional<Candidat> findById(int candidatId);

    int save(Candidat candidat);
}
