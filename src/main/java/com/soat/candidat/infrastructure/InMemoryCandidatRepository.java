package com.soat.candidat.infrastructure;

import com.soat.candidat.domain.Candidat;
import com.soat.candidat.domain.CandidatRepository;

import java.util.HashMap;
import java.util.UUID;

public class InMemoryCandidatRepository implements CandidatRepository {
    private final HashMap<UUID, Candidat> candidats = new HashMap<>();
    private UUID fixedUUID;

    public InMemoryCandidatRepository(UUID fixedUUID) {
        this.fixedUUID = fixedUUID;
    }

    @Override
    public UUID next() {
        return fixedUUID;
    }

    @Override
    public void save(Candidat candidat) {
        candidats.put(candidat.getId(), candidat);
    }

    public Candidat findById(UUID candidatId) {
        return candidats.get(candidatId);
    }
}
