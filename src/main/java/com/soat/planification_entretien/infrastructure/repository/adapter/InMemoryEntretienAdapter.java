package com.soat.planification_entretien.infrastructure.repository.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soat.planification_entretien.application.use_case.output_port.EntretienPort;
import com.soat.planification_entretien.domain.model.Candidat;
import com.soat.planification_entretien.domain.model.Entretien;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class InMemoryEntretienAdapter implements EntretienPort {
    private final Map<Integer, Entretien> map = new HashMap<>();

    @Override
    public Entretien findByCandidat(Candidat candidat) {
        return map.get(candidat.getId());
    }

    @Override
    public void save(Entretien entretien) {
        int id = map.size() + 1;
        Entretien toSave = new Entretien(id, entretien.getCandidat(), entretien.getRecruteur(), entretien.getHoraireEntretien());
        map.put(id, toSave);
    }

    @Override
    public List<Entretien> findAll() {
        return map.values().stream().toList();
    }
}
