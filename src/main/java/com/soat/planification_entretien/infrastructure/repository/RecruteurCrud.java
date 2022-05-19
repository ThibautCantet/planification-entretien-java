package com.soat.planification_entretien.infrastructure.repository;

import com.soat.planification_entretien.domain.model.Recruteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruteurCrud extends JpaRepository<Recruteur, Integer> {
}
