package com.soat.planification_entretien.application.use_case.output_port;

import java.util.Collection;

import com.soat.planification_entretien.domain.model.Candidat;
import com.soat.planification_entretien.domain.model.Entretien;

public interface EntretienPort {
    Collection<Entretien> findAll();

    void save(Entretien entretien);

    Entretien findByCandidat(Candidat candidat);
}
