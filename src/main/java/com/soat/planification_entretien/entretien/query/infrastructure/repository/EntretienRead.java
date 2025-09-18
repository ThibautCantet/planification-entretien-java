package com.soat.planification_entretien.entretien.query.infrastructure.repository;

import java.util.List;

import com.soat.planification_entretien.entretien.infrastructure.repository.HibernateEntretien;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntretienRead extends CrudRepository<HibernateEntretien, Integer> {

    List<HibernateEntretien> findAll();
}
