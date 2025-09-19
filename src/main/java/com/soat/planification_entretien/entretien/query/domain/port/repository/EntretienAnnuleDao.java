package com.soat.planification_entretien.entretien.query.domain.port.repository;

public interface EntretienAnnuleDao {
    void incrementerEntretienAnnule();

    Integer getNbEntretiensAnnules();
}
