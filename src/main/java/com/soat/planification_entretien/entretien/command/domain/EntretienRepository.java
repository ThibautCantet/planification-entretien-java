package com.soat.planification_entretien.entretien.command.domain;

import java.util.List;
import java.util.Optional;

public interface EntretienRepository {
    void save(Entretien entretien);

    List<Entretien> findAll();

    Entretien findByCandidatId(int candidat);

    Optional<Entretien> findById(int entretienId);
}
