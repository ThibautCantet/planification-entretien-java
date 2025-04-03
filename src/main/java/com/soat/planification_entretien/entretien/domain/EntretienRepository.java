package com.soat.planification_entretien.entretien.domain;

import java.util.List;


public interface EntretienRepository {
    void save(Entretien entretien);

    List<Entretien> findAll();

    Entretien findByEmail(String email);
}
