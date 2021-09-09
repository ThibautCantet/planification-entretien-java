package com.soat.planification_entretien.domain;

public interface EntretienRepository {
    Entretien findByCandidat(Candidat candidat);

    void save(Entretien entretien);
}
