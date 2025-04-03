package com.soat.planification_entretien.entretien.domain;

import java.util.Optional;

public interface ConsultantRecruteurRepository {
    Optional<ConsultantRecruteur> findById(int id);
}
