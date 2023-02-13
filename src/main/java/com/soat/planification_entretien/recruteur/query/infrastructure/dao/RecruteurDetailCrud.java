package com.soat.planification_entretien.recruteur.query.infrastructure.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruteurDetailCrud extends JpaRepository<JpaRecruteurDetail, Integer> {
}
