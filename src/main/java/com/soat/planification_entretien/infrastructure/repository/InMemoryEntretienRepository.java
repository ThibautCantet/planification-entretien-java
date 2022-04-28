package com.soat.planification_entretien.infrastructure.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soat.planification_entretien.domain.Candidat;
import com.soat.planification_entretien.domain.Entretien;
import com.soat.planification_entretien.domain.EntretienRepository;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryEntretienRepository implements EntretienRepository {
    private final Map<Integer, Entretien> map = new HashMap<>();

    @Override
    public Entretien findByCandidat(Candidat candidat) {
        return map.get(candidat.getId());
    }

    @Override
    public Entretien save(Entretien entretien) {
        int id = map.size() + 1;
        Entretien toSave = Entretien.of(id, entretien.getCandidat(), entretien.getRecruteur(), entretien.getHoraireEntretien());
        map.put(id, toSave);
        return toSave;
    }

    @Override
    public List<Entretien> findAll() {
        return map.values().stream().toList();
    }
}
