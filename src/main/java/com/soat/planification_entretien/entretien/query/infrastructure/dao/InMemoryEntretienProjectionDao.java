package com.soat.planification_entretien.entretien.query.infrastructure.dao;

import com.soat.planification_entretien.entretien.query.application.EntretienProjectionDao;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryEntretienProjectionDao implements EntretienProjectionDao {

    private int count;

    @Override
    public void incrementEntretienAnnule() {
        count++;
    }

    @Override
    public int entretiensAnnules() {
        return count;
    }
}
