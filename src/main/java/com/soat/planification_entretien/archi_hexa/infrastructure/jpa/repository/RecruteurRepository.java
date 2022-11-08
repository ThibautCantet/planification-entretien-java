package com.soat.planification_entretien.archi_hexa.infrastructure.jpa.repository;

import com.soat.planification_entretien.archi_hexa.infrastructure.jpa.model.JpaRecruteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruteurRepository extends JpaRepository<JpaRecruteur, Integer> {
}
