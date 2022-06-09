package com.soat.planification_entretien.domain.entretien.command.repository;

import com.soat.planification_entretien.domain.entretien.command.entity.Candidat;
import com.soat.planification_entretien.domain.entretien.command.entity.Entretien;

public interface EntretienRepository {
    int save(Entretien entretien);

    Entretien findByCandidat(Candidat candidat);

    //todo move
    Entretien findById(int id);
}
