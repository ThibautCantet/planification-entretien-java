package com.soat.planification_entretien.entretien.command.domain.repository;

import java.util.UUID;

import com.soat.planification_entretien.entretien.command.domain.entity.Entretien;
import com.soat.planification_entretien.entretien.command.domain.entity.Candidat;

public interface EntretienRepository {
    UUID next();

    String save(Entretien entretien);

    Entretien findByCandidat(Candidat candidat);

    //todo move
    Entretien findById(String id);
}
