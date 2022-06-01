package com.soat.planification_entretien.domain.entretien;

import java.util.List;

import com.soat.planification_entretien.domain.candidat.Candidat;

public interface EntretienRepository {
    int save(Entretien entretien);

    List<Entretien> findAll();

    Entretien findByCandidat(Candidat candidat);

    Entretien findById(int id);
}
