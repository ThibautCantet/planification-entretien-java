package com.soat.planification_entretien.infrastructure.repository;

import java.util.List;

import com.soat.planification_entretien.domain.model.Entretien;
import com.soat.planification_entretien.use_case.EntretienRepository;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateEntretienRepository implements EntretienRepository {
    private final EntretienCrud entretienCrud;

    public HibernateEntretienRepository(EntretienCrud entretienCrud) {
        this.entretienCrud = entretienCrud;
    }

    @Override
    public void save(Entretien entretien) {
        entretienCrud.save(entretien);
    }

    @Override
    public List<Entretien> findAll() {
        return entretienCrud.findAll();
    }
}
