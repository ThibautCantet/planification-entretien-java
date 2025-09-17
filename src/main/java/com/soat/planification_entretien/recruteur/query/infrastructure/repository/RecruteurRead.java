package com.soat.planification_entretien.recruteur.query.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruteurRead extends JpaRepository<HibernateRecruteurRead, Integer> {
}
