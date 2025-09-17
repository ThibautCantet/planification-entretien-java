package com.soat.planification_entretien.entretien.domain.port.repository;

import java.util.List;
import java.util.Optional;

import com.soat.planification_entretien.entretien.domain.model.Entretien;

public interface EntretienRepository {
    void save(Entretien entretien);

    List<Entretien> findAll();

    Entretien findByCandidatId(int candidat);

    Optional<Entretien> findById(int entretienId);
}
