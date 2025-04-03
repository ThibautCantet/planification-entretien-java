package com.soat.planification_entretien.entretien.domain;

import java.util.List;


public interface EntretienRepository {
    void save(Entretien entretien);

    Entretien findById(EntretienId entretienId);
    List<Entretien> findAll();

    Entretien findByEmail(String email);
}
