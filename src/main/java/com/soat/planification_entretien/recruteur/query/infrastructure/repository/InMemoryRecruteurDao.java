package com.soat.planification_entretien.recruteur.query.infrastructure.repository;

import java.util.ArrayList;
import java.util.List;

import com.soat.planification_entretien.recruteur.query.domain.model.RecruteurDetail;
import com.soat.planification_entretien.recruteur.query.domain.port.repository.RecruteurDao;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryRecruteurDao implements RecruteurDao {

    private final List<RecruteurDetail> recruteursExperimentes = new ArrayList<>();

    @Override
    public List<RecruteurDetail> find10AnsExperience() {
        return recruteursExperimentes;
    }

    @Override
    public void addExperimente(RecruteurDetail recruteurDetail) {
        recruteursExperimentes.add(recruteurDetail);
    }

}
