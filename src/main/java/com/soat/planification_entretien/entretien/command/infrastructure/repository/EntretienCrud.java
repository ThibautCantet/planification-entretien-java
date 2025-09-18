package com.soat.planification_entretien.entretien.command.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import com.soat.planification_entretien.entretien.infrastructure.repository.HibernateEntretien;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntretienCrud extends CrudRepository<HibernateEntretien, Integer> {
    Optional<HibernateEntretien> findByCandidat_Id(Integer id);

    HibernateEntretien save(HibernateEntretien entretien);
}
