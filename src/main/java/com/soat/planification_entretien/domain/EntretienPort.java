package com.soat.planification_entretien.domain;

import java.util.List;

public interface EntretienPort {
    List<Entretien> findAll();

    void save(Entretien entretien);
}
