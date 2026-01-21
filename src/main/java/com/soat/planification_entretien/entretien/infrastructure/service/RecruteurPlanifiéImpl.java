package com.soat.planification_entretien.entretien.infrastructure.service;

import java.util.List;

import com.soat.planification_entretien.entretien.domain.aggregate.RecruteurPlanifié;
import com.soat.planification_entretien.entretien.domain.application_service.RecruteurPlanifieService;
import com.soat.planification_entretien.recruteur.domain.RecruteurRepository;
import org.springframework.stereotype.Service;

@Service
public class RecruteurPlanifiéImpl implements RecruteurPlanifieService {

    private final RecruteurRepository recruteurRepository;

    public RecruteurPlanifiéImpl(RecruteurRepository recruteurRepository) {
        this.recruteurRepository = recruteurRepository;
    }

    @Override
    public List<RecruteurPlanifié> findAll() {
        var listeRecruteurs = recruteurRepository.findAll();
        return listeRecruteurs.stream()
                .map(recruteur -> new RecruteurPlanifié(
                        recruteur.getId(),
                        recruteur.getLanguage(),
                        recruteur.getEmail(),
                        recruteur.getExperienceInYears(),
                        recruteur.estDisponible()
                ))
                .toList();
    }
}
