package com.soat.planification_entretien.entretien.command.domain.repository;

import com.soat.planification_entretien.entretien.command.domain.entity.Entretien;
import com.soat.planification_entretien.entretien.command.domain.entity.Candidat;

public interface EntretienRepository {
    int save(Entretien entretien);

    Entretien findByCandidat(Candidat candidat);

    //todo move
    Entretien findById(int id);
}
