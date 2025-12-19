package com.soat.planification_entretien.infrastructure.repository.adapter.inmemory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.soat.planification_entretien.domain.Recruteur;
import com.soat.planification_entretien.domain.RecruteurPort;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class InMemoryRecruteurRepository implements RecruteurPort {
    private final Map<Integer, Recruteur> map = new HashMap<>();

    @Override
    public Recruteur save(Recruteur recruteur) {
        int id = map.size() + 1;
        Recruteur toSave = new Recruteur(id, recruteur.getLanguage(), recruteur.getEmail(), recruteur.getExperienceInYears());
        map.put(id, toSave);
        return toSave;
    }

    @Override
    public List<Recruteur> findExperimentes() {
        return map.values().stream().filter(r -> r.getExperienceInYears() >= 10)
                .toList();
    }


    @Override
    public Optional<Recruteur> findById(int recruteurId) {
        return Optional.ofNullable(map.getOrDefault(recruteurId, null));
    }

}
