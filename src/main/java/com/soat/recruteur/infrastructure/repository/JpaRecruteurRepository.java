package com.soat.recruteur.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.soat.recruteur.domain.Recruteur;
import com.soat.recruteur.domain.RecruteurRepository;
import com.soat.shared.infrastructure.repository.JpaRecruteurCrudRepository;
import org.springframework.stereotype.Repository;

@Repository("recruteur")
public class JpaRecruteurRepository implements RecruteurRepository {
    private final JpaRecruteurCrudRepository jpaRecruteurCrudRepository;

    public JpaRecruteurRepository(JpaRecruteurCrudRepository jpaRecruteurCrudRepository) {
        this.jpaRecruteurCrudRepository = jpaRecruteurCrudRepository;
    }

    @Override
    public UUID next() {
        return UUID.randomUUID();
    }

    @Override
    public Recruteur save(Recruteur recruteur) {
        var jpaRecruteur = new com.soat.shared.infrastructure.repository.model.Recruteur(recruteur.getId(), recruteur.getLanguage(), recruteur.getEmail(), recruteur.getAnneesExperience());
        jpaRecruteurCrudRepository.save(jpaRecruteur);
        return recruteur;
    }

    @Override
    public List<Recruteur> findAll() {
        return jpaRecruteurCrudRepository.findAll()
                .stream()
                .map(r -> new Recruteur(r.getId(), r.getLanguage(), r.getEmail(), r.getExperienceInYears()))
                .toList();
    }

    @Override
    public Optional<Recruteur> findById(UUID recruteurId) {
        var recruteur = jpaRecruteurCrudRepository.findById(recruteurId);
        return recruteur.map(r -> new Recruteur(recruteurId, r.getLanguage(), r.getEmail(), r.getExperienceInYears()));
    }
}
