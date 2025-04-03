package com.soat.planification_entretien.entretien.infrastructure.respository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soat.planification_entretien.entretien.domain.Entretien;
import com.soat.planification_entretien.entretien.domain.EntretienId;
import com.soat.planification_entretien.entretien.domain.EntretienRepository;

//@Repository
public class InMemoryEntretienRepository implements EntretienRepository {

    private final Map<Integer, Entretien> cache = new HashMap<>();

    @Override
    public void save(Entretien entretien) {
        Integer newId = cache.size() + 1;
        entretien = Entretien.of(newId, entretien);
        cache.put(entretien.getId(), entretien);
    }

    @Override
    public Entretien findById(EntretienId entretienId) {
        return null;
    }

    @Override
    public List<Entretien> findAll() {
        return cache.values().stream().toList();
    }

    @Override
    public Entretien findByEmail(String email) {
        return cache.values().stream()
                .filter(entretien -> entretien.getCandidat().email() == email)
                .findFirst()
                .orElse(null);
    }
}
