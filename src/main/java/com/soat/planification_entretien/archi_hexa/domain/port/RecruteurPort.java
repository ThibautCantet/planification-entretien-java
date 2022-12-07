package com.soat.planification_entretien.archi_hexa.domain.port;

import com.soat.planification_entretien.archi_hexa.domain.model.Recruteur;

import java.util.Optional;

public interface RecruteurPort {
    Optional<Recruteur> findById(int id);
}
