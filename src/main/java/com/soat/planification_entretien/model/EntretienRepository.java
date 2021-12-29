package com.soat.planification_entretien.model;

public interface EntretienRepository {
    Entretien findByCandidat(Candidat candidat);

    void save(Entretien entretien);
}
