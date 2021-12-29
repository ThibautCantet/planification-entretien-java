package com.soat.planification_entretien.repository;

import com.soat.planification_entretien.model.Candidat;
import com.soat.planification_entretien.model.Entretien;

public interface EntretienRepository {
    Entretien findByCandidat(Candidat candidat);

    void save(Entretien entretien);
}
