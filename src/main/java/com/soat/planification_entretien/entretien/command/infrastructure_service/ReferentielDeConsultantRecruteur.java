package com.soat.planification_entretien.entretien.command.infrastructure_service;

import java.util.List;

import com.soat.planification_entretien.entretien.command.domain.entity.Recruteur;

public interface ReferentielDeConsultantRecruteur {
    List<Recruteur> findAll();
}
