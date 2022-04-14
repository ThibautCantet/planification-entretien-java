package com.soat.planification_entretien.infrastructure.repository;

import com.soat.planification_entretien.infrastructure.model.Recruteur;
import com.soat.planification_entretien.use_case.RecruteurRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaRecruteurRepository extends RecruteurRepository, JpaRepository<Recruteur, Integer> {
}
