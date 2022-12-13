package com.soat.planification_entretien.archi_hexa.domain.port;

import com.soat.planification_entretien.archi_hexa.domain.model.Candidat;

import java.util.Optional;

public interface CandidatPort {
    Optional<Candidat> findById(int id);

    Integer save(Candidat candidat);
}
