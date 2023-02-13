package com.soat.planification_entretien.recruteur.query.infrastructure.dao;

import java.util.ArrayList;
import java.util.List;

import com.soat.planification_entretien.recruteur.query.application.RecruteurDao;
import com.soat.planification_entretien.recruteur.query.application.RecruteurDetail;

public class InMemoryRecruteurDao implements RecruteurDao {
    private final List<RecruteurDetail> cache = new ArrayList<>();

    @Override
    public List<RecruteurDetail> find10AnsExperience() {
        return cache;
    }

    @Override
    public void addExperimente(RecruteurDetail recruteurDetail) {
        cache.add(recruteurDetail);
    }
}
