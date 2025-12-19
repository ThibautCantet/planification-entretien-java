package com.soat.planification_entretien.infrastructure.repository.adapter.inmemory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soat.planification_entretien.domain.Entretien;
import com.soat.planification_entretien.domain.EntretienPort;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class InMemoryEntretienAdapter implements EntretienPort {
    private final Map<Integer, Entretien> map = new HashMap<>();

    @Override
    public void save(Entretien entretien) {
        int id = map.size() + 1;
        Entretien toSave = Entretien.of(id, entretien.getCandidat(), entretien.getRecruteur(), entretien.getHoraireEntretien());
        map.put(id, toSave);
    }

    @Override
    public Entretien findByCandidatId(Integer candidatId) {
        return map.values().stream()
                .filter(entretien -> entretien.getCandidat().getId().equals(candidatId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Entretien> findAll() {
        return map.values().stream().toList();
    }
}
