package com.soat.planification_entretien.infrastructure.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.soat.planification_entretien.domain.recruteur.command.entity.Recruteur;
import com.soat.planification_entretien.domain.recruteur.command.repository.RecruteurRepository;
import com.soat.planification_entretien.domain.recruteur.query.dao.RecruteurDAO;

//@Repository
public class InMemoryRecruteurRepository implements RecruteurRepository, RecruteurDAO {
    private final Map<Integer, Recruteur> cache = new HashMap<>();

    @Override
    public Optional<Recruteur> findById(int recruteurId) {
        return Optional.ofNullable(cache.get(recruteurId));
    }

    @Override
    public Recruteur save(Recruteur recruteur) {
        Integer newId = cache.size() + 1;
        recruteur = Recruteur.of(newId, recruteur);
        cache.put(recruteur.getId(), recruteur);
        return recruteur;
    }

    @Override
    public List<com.soat.planification_entretien.domain.recruteur.query.dto.Recruteur> find10AnsExperience() {
        return cache.values().stream()
                .filter(recruteur -> recruteur.getExperienceInYears() >= 10)
                .map(r -> new com.soat.planification_entretien.domain.recruteur.query.dto.Recruteur(r.getId(), r.getLanguage(), r.getEmail(), r.getExperienceInYears()))
                .toList();
    }
}
