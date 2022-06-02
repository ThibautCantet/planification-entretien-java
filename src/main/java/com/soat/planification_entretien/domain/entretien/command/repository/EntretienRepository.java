package com.soat.planification_entretien.domain.entretien.command.repository;

import java.util.List;

import com.soat.planification_entretien.domain.candidat.entity.Candidat;
import com.soat.planification_entretien.domain.entretien.command.entity.Entretien;

public interface EntretienRepository {
    int save(Entretien entretien);

    List<Entretien> findAll();

    Entretien findByCandidat(Candidat candidat);

    Entretien findById(int id);
}
