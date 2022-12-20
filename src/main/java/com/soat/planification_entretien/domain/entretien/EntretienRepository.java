package com.soat.planification_entretien.domain.entretien;

import java.util.List;

public interface EntretienRepository {
    void save(Entretien entretien);

    List<Entretien> findAll();

    Entretien findByCandidatId(int candidat);
}
