package com.soat.planification_entretien.infrastructure.repository.adapter;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.soat.planification_entretien.application.use_case.output_port.RecruteurPort;
import com.soat.planification_entretien.domain.model.Recruteur;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import static java.util.Optional.*;

@Repository
@Primary
public class InMemoryRecruteurRepository implements RecruteurPort {
    private final Map<Integer, Recruteur> map = new HashMap<>();

    @Override
    public int save(Recruteur recruteur) {
        int id = map.size() + 1;
        Recruteur toSave = new Recruteur(id, recruteur.getLanguage(), recruteur.getEmail(), recruteur.getExperienceInYears());
        map.put(id, toSave);
        return id;
    }

    @Override
    public List<Recruteur> findAll() {
        return map.values().stream().toList();
    }

    @Override
    public Optional<Recruteur> findById(int recruteurId) {
        return Optional.ofNullable(map.getOrDefault(recruteurId, null));
    }

}
