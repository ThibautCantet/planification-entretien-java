package com.soat.planification_entretien.infrastructure.repository;

import java.util.Optional;

import com.soat.planification_entretien.use_case.RecruteurRepository;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateRecruteurRepository implements RecruteurRepository {
    private final RecruteurCrud recruteurCrud;

    public HibernateRecruteurRepository(RecruteurCrud recruteurCrud) {
        this.recruteurCrud = recruteurCrud;
    }

    @Override
    public Optional<com.soat.planification_entretien.domain.model.Recruteur> findById(int recruteurId) {
        return recruteurCrud.findById(recruteurId).map(
                recruteur -> new com.soat.planification_entretien.domain.model.Recruteur(
                        recruteurId,
                        recruteur.getLanguage(),
                        recruteur.getEmail(),
                        recruteur.getExperienceInYears()
                )
        );
    }
}
