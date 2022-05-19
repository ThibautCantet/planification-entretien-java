package com.soat.planification_entretien.use_case;

import java.util.List;

import com.soat.planification_entretien.domain.model.Entretien;

public interface EntretienRepository {
    void save(Entretien entretien);

    List<Entretien> findAll();
}
