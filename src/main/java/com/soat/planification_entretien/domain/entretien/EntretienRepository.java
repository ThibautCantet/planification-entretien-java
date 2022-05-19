package com.soat.planification_entretien.domain.entretien;

import java.util.List;

import com.soat.planification_entretien.domain.entretien.Entretien;

public interface EntretienRepository {
    void save(Entretien entretien);

    List<Entretien> findAll();
}
