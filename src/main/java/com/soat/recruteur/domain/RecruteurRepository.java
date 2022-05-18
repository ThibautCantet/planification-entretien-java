package com.soat.recruteur.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RecruteurRepository {
    UUID next();

    Recruteur save(Recruteur recruteur);

    List<Recruteur> findAll();

    Optional<Recruteur> findById(UUID recruteurId);
}
