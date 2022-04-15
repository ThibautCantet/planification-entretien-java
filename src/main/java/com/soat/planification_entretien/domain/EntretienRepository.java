package com.soat.planification_entretien.domain;

import java.util.List;

public interface EntretienRepository {
    Entretien findByCandidat(Candidat candidat);

    Entretien save(Entretien entretien);

    List<Entretien> findAll();
}
