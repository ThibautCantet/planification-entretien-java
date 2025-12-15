package com.soat.planification_entretien.infrastructure.repository;

import java.util.Optional;

import com.soat.planification_entretien.domain.Recruteur;
import com.soat.planification_entretien.domain.RecruteurPort;
import org.springframework.stereotype.Repository;

@Repository
public class RecruteurAdapter implements RecruteurPort {
    private final RecruteurRepository recruteurRepository;

    public RecruteurAdapter(RecruteurRepository recruteurRepository) {
        this.recruteurRepository = recruteurRepository;
    }

    @Override
    public Optional<Recruteur> findById(int recruteurId) {
        return recruteurRepository.findById(recruteurId)
                .map(r -> new Recruteur(r.getId(), r.getLanguage(), r.getEmail(), r.getExperienceInYears()));
    }

    @Override
    public Recruteur save(Recruteur recruteur) {
        var toSave = new JpaRecruteur(recruteur.getLanguage(), recruteur.getEmail(), recruteur.getExperienceInYears());
        var saved = recruteurRepository.save(toSave);
        return new Recruteur(saved.getId(), saved.getLanguage(), saved.getEmail(), saved.getExperienceInYears());
    }
}
