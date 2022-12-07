package com.soat.planification_entretien.archi_hexa.infrastructure.jpa.repository;

import java.util.List;

import com.soat.planification_entretien.archi_hexa.infrastructure.jpa.model.JpaCandidat;
import com.soat.planification_entretien.archi_hexa.infrastructure.jpa.model.JpaEntretien;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaEntretienRepository extends CrudRepository<JpaEntretien, Integer> {
    JpaEntretien findByCandidat(JpaCandidat candidat);

    JpaEntretien save(JpaEntretien entretien);

    List<JpaEntretien> findAll();
}
