package com.soat.planification_entretien.entretien.command.domain;

import java.util.Optional;

public interface EntretienRepository {
    void save(Entretien entretien);

    Entretien findByCandidatId(int candidat);

    Optional<Entretien> findById(int entretienId);
}
