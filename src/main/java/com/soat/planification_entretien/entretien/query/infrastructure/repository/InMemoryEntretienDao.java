package com.soat.planification_entretien.entretien.query.infrastructure.repository;

import com.soat.planification_entretien.entretien.query.domain.port.repository.EntretienAnnuleDao;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryEntretienDao implements EntretienAnnuleDao {

    private Integer nbEntretiensAnnules = 0;

    @Override
    public void incrementerEntretienAnnule() {
        this.nbEntretiensAnnules++;
    }

    @Override
    public Integer getNbEntretiensAnnules() {
        return nbEntretiensAnnules;
    }
}
