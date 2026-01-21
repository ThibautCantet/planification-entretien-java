package com.soat.planification_entretien.entretien.domain.domain_service;


import com.soat.planification_entretien.entretien.domain.aggregate.Candidat;
import com.soat.planification_entretien.entretien.domain.aggregate.RecruteurPlanifié;
import com.soat.planification_entretien.entretien.domain.application_service.RecruteurPlanifieService;
import org.springframework.stereotype.Service;

@Service
public class RecruteurDisponible {

    private final RecruteurPlanifieService recruteurPlanifieService;

    public RecruteurDisponible(RecruteurPlanifieService recruteurPlanifieService) {
        this.recruteurPlanifieService = recruteurPlanifieService;
    }

    public RecruteurPlanifié trouver(Candidat candidat) {
        return recruteurPlanifieService.findAll()
                .stream()
                .filter(RecruteurPlanifié::estDisponible)
                .filter(recruteur -> recruteur.estCompatibleAvec(candidat))
                .findFirst()
                .orElse(null);
    }

}
