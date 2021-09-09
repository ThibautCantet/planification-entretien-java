package com.soat.planification_entretien.infrastructure;

import com.soat.planification_entretien.domain.Candidat;
import com.soat.planification_entretien.domain.Entretien;
import com.soat.planification_entretien.domain.EntretienRepository;

import java.util.HashMap;

public class InMemoryEntretienRepository implements EntretienRepository {

    private HashMap<Candidat, Entretien> candidatEntretienHashMap= new HashMap<>();

    @Override
    public Entretien findByCandidat(Candidat candidat) {
        return candidatEntretienHashMap.get(candidat);
    }
}
