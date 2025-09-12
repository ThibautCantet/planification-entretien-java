package com.soat.planification_entretien.infrastructure.preparation.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.soat.planification_entretien.domain.preparation.Recruteur;
import com.soat.planification_entretien.domain.preparation.RecruteurRepository;

//@Repository
public class InMemoryRecruteurRepository implements RecruteurRepository {
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
    public List<Recruteur> find10AnsExperience() {
        return cache.values().stream()
                .filter(recruteur -> recruteur.getExperienceInYears() >= 10)
                .toList();
    }

    @Override
    public List<Recruteur> findAll() {
        return cache.values().stream().toList();
    }

    @Override
    public Recruteur findByEmail(String email){
        return cache.values().stream()
                .filter(recruteur -> recruteur.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }


}
