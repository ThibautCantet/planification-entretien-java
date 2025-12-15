package com.soat.planification_entretien.infrastructure.repository;

import java.util.List;

import com.soat.planification_entretien.domain.Entretien;
import com.soat.planification_entretien.domain.EntretienPort;
import org.springframework.stereotype.Repository;

@Repository
public class EntretienAdapter implements EntretienPort {

    private final EntretienRepository entretienRepository;

    public EntretienAdapter(EntretienRepository entretienRepository) {
        this.entretienRepository = entretienRepository;
    }

    @Override
    public List<Entretien> findAll() {
        return entretienRepository.findAll();
    }

    @Override
    public void save(Entretien entretien) {
        entretienRepository.save(entretien);
    }
}
