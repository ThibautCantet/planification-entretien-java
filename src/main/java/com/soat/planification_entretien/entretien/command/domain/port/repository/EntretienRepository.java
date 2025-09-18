package com.soat.planification_entretien.entretien.command.domain.port.repository;

import java.util.Optional;

import com.soat.planification_entretien.entretien.command.domain.model.Entretien;

public interface EntretienRepository {
    void save(Entretien entretien);

    Entretien findByCandidatId(int candidat);

    Optional<Entretien> findById(int entretienId);
}
