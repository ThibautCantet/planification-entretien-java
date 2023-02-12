package com.soat.planification_entretien.recruteur.command.infrastucture.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.soat.planification_entretien.recruteur.command.domain.Recruteur;
import com.soat.planification_entretien.recruteur.command.domain.RecruteurRepository;
import com.soat.planification_entretien.recruteur.query.infrastructure.dao.IRecruteurDetailImpl;
import com.soat.planification_entretien.recruteur.query.application.IRecruteurDetail;
import com.soat.planification_entretien.recruteur.query.application.RecruteurDao;

//@Repository
public class InMemoryRecruteurRepository implements RecruteurRepository, RecruteurDao {
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
    public List<IRecruteurDetail> find10AnsExperience() {
        return cache.values().stream()
                .filter(recruteur -> recruteur.getExperienceInYears() >= 10)
                .map(recruteur -> new IRecruteurDetailImpl(
                        recruteur.getId(),
                        recruteur.getLanguage(),
                        recruteur.getExperienceInYears(),
                        recruteur.getAdresseEmail()))
                .map(r -> (IRecruteurDetail) r)
                .toList();
    }

    @Override
    public Optional<Recruteur> findByEmail(String email) {
        return Optional.empty();
    }
}
