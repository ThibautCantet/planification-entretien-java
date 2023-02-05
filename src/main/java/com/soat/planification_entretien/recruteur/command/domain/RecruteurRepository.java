package com.soat.planification_entretien.recruteur.command.domain;

import java.util.Optional;

public interface RecruteurRepository {
    Optional<Recruteur> findById(int recruteurId);

    Recruteur save(Recruteur recruteur);

    Optional<Recruteur> findByEmail(String email);
}
