package com.soat.planification_entretien.domain.planification;

import java.util.List;

public interface EntretienRepository {
    void save(Entretien entretien);

    List<Entretien> findAll();

    Entretien findByCandidat(CandidatSuivi candidat);

    Entretien findById(int id);

}
