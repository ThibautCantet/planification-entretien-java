package com.soat.planification_entretien.infrastructure.repository;

import java.util.Optional;

import com.soat.planification_entretien.domain.recruteur.RecruteurRepository;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateRecruteurRepository implements RecruteurRepository {
    private final RecruteurCrud recruteurCrud;

    public HibernateRecruteurRepository(RecruteurCrud recruteurCrud) {
        this.recruteurCrud = recruteurCrud;
    }

    @Override
    public Optional<com.soat.planification_entretien.domain.recruteur.Recruteur> findById(int recruteurId) {
        return recruteurCrud.findById(recruteurId).map(
                recruteur -> new com.soat.planification_entretien.domain.recruteur.Recruteur(
                        recruteurId,
                        recruteur.getLanguage(),
                        recruteur.getEmail(),
                        recruteur.getExperienceInYears()
                )
        );
    }

    @Override
    public com.soat.planification_entretien.domain.recruteur.Recruteur save(com.soat.planification_entretien.domain.recruteur.Recruteur recruteur) {
        com.soat.planification_entretien.infrastructure.repository.Recruteur toSave = new com.soat.planification_entretien.infrastructure.repository.Recruteur(recruteur.getLanguage(), recruteur.getEmail(), recruteur.getExperienceInYears());
        com.soat.planification_entretien.infrastructure.repository.Recruteur saved = recruteurCrud.save(toSave);
        return com.soat.planification_entretien.domain.recruteur.Recruteur.of(saved.getId(), recruteur);
    }
}