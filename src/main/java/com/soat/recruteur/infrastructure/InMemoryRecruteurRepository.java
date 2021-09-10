package com.soat.recruteur.infrastructure;

import com.soat.recruteur.domain.Recruteur;
import com.soat.recruteur.domain.RecruteurRepository;

import java.util.HashMap;
import java.util.UUID;

public class InMemoryRecruteurRepository implements RecruteurRepository {
    private final HashMap<UUID, Recruteur> recruteurs = new HashMap<>();
    private final UUID fixedUUID;

    public InMemoryRecruteurRepository(UUID fixedUUID) {
        this.fixedUUID = fixedUUID;
    }

    @Override
    public UUID next() {
        return fixedUUID;
    }

    @Override
    public void save(Recruteur recruteur) {
        recruteurs.put(recruteur.getId(), recruteur);
    }

    public Recruteur findById(UUID recruteurId) {
        return recruteurs.get(recruteurId);
    }
}
