package com.soat.planification_entretien.archi_hexa.infrastructure.repository;

import com.soat.planification_entretien.archi_hexa.infrastructure.model.Recruteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruteurRepository extends JpaRepository<Recruteur, Integer> {
}
