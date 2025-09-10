package com.soat.planification_entretien.domain.planification;

import java.util.List;

import com.soat.planification_entretien.domain.preparation.Candidat;

public interface EntretienRepository {
    void save(Entretien entretien);

    List<Entretien> findAll();

    Entretien findByCandidat(Candidat candidat);
}
