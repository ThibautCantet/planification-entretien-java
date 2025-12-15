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
        return recruteurRepository.findById(recruteurId);
    }

    @Override
    public Recruteur save(Recruteur recruteur) {
        return recruteurRepository.save(recruteur);
    }
}
