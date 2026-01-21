package com.soat.planification_entretien.entretien.domain.application_service;

import java.util.List;

import com.soat.planification_entretien.entretien.domain.aggregate.RecruteurPlanifié;

public interface RecruteurPlanifieService {

    List<RecruteurPlanifié> findAll();

}
