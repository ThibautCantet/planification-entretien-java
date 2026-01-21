package com.soat.planification_entretien.entretien.domain.aggregate;

import java.util.List;

import com.soat.planification_entretien.candidat.domain.CandidatProspect;

public interface EntretienRepository {
    void save(Entretien entretien);

    List<Entretien> findAll();

    Entretien findById(int entretienId);

    Entretien findByCandidat(CandidatProspect candidat);
}
