package com.soat.planification_entretien.use_case;

import java.util.List;

import com.soat.planification_entretien.domain.Candidat;
import com.soat.planification_entretien.domain.Entretien;

public interface EntretienRepository {
    Entretien findByCandidat(Candidat candidat);

    Entretien save(Entretien entretien);

    List<Entretien> findAll();
}
