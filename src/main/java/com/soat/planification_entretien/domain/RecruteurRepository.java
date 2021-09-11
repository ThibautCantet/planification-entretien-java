package com.soat.planification_entretien.domain;

import java.util.UUID;

public interface RecruteurRepository {
    Recruteur findById(UUID recruteurId);
}
