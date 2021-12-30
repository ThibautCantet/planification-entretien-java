package com.soat.planification_entretien.repository;

import com.soat.planification_entretien.model.Candidat;
import com.soat.planification_entretien.model.Entretien;

import java.util.HashMap;

public class InMemoryEntretienRepository implements EntretienRepository {

    private HashMap<Candidat, Entretien> candidatEntretienHashMap= new HashMap<>();

    @Override
    public Entretien findByCandidat(Candidat candidat) {
        return candidatEntretienHashMap.get(candidat);
    }

    @Override
    public Entretien save(Entretien entretien) {
        candidatEntretienHashMap.put(entretien.getCandidat(), entretien);
        return entretien;
    }
}
