package com.soat.recruteur.domain;

import java.util.List;
import java.util.Optional;

public interface RecruteurRepository {
    Recruteur save(Recruteur recruteur);

    List<Recruteur> findAll();

    Optional<Recruteur> findById(int recruteurId);
}
