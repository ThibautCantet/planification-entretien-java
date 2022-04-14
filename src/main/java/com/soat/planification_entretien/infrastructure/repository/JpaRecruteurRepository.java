package com.soat.planification_entretien.infrastructure.repository;

import java.util.Optional;

import com.soat.planification_entretien.domain.Recruteur;
import com.soat.planification_entretien.use_case.RecruteurRepository;
import org.springframework.stereotype.Repository;

@Repository
public class JpaRecruteurRepository implements RecruteurRepository {
    private final JpaRecruteurCrudRepository jpaRecruteurCrudRepository;

    public JpaRecruteurRepository(JpaRecruteurCrudRepository jpaRecruteurCrudRepository) {
        this.jpaRecruteurCrudRepository = jpaRecruteurCrudRepository;
    }

    @Override
    public Recruteur save(Recruteur recruteur) {
        var jpaRecruteur = new com.soat.planification_entretien.infrastructure.model.Recruteur(recruteur.getLanguage(), recruteur.getEmail(), recruteur.getExperienceInYears());
        var saved = jpaRecruteurCrudRepository.save(jpaRecruteur);
        return Recruteur.of(saved.getId(), recruteur);
    }

    @Override
    public Optional<Recruteur> findById(int recruteurId) {
        var recruteur = jpaRecruteurCrudRepository.findById(recruteurId);
        return recruteur.map(r -> Recruteur.of(recruteurId, r.getLanguage(), r.getEmail(), r.getExperienceInYears()));
    }
}
