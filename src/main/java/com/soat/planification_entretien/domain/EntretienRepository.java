package com.soat.planification_entretien.domain;

public interface EntretienRepository {
    Entretien findByCandidat(Candidat candidat);

    Entretien save(Entretien entretien);
}
