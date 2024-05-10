package com.soat.planification_entretien.infrastructure.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.soat.planification_entretien.domain.Recruteur;
import com.soat.planification_entretien.domain.RecruteurRepository;
import org.springframework.stereotype.Repository;

import static java.util.Optional.*;

@Repository
public class InMemoryRecruteurRepository implements RecruteurRepository {
    private final Map<Integer, Recruteur> map = new HashMap<>();

    @Override
    public Recruteur save(Recruteur recruteur) {
        int id = map.size() + 1;
        Recruteur toSave = Recruteur.of(id, recruteur.getLanguage(), recruteur.getEmail(), recruteur.getExperienceInYears());
        map.put(id, toSave);
        return toSave;
    }

    @Override
    public Optional<Recruteur> findById(int recruteurId) {
        var recruteur = map.get(recruteurId);
        if (recruteur != null) {
            return Optional.of(recruteur);
        }
        return Optional.empty();
    }

    @Override
    public List<Recruteur> findAll() {
        return map.values().stream().toList();
    }
}
