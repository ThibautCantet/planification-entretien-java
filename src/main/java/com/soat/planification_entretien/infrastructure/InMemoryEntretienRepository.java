package com.soat.planification_entretien.infrastructure;

import com.soat.planification_entretien.domain.Entretien;
import com.soat.planification_entretien.domain.EntretienRepository;

import java.util.HashMap;
import java.util.UUID;

public class InMemoryEntretienRepository implements EntretienRepository {

    private final UUID fixedNextId;

    private final HashMap<UUID, Entretien> candidatEntretienHashMap= new HashMap<>();

    public InMemoryEntretienRepository(UUID fixedNextId) {
        this.fixedNextId = fixedNextId;
    }

    @Override
    public UUID next() {
        return fixedNextId;
    }

    @Override
    public Entretien findById(UUID entretienId) {
        return candidatEntretienHashMap.get(entretienId);
    }

    @Override
    public void save(Entretien entretien) {
        candidatEntretienHashMap.put(entretien.getId(), entretien);
    }
}
