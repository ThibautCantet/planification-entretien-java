package com.soat.planification_entretien.domain;

import com.soat.recruteur.domain.Recruteur;

import java.util.UUID;

public interface RecruteurRepository {
    Recruteur findById(UUID recruteurId);
}
