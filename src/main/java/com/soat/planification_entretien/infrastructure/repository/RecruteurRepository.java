package com.soat.planification_entretien.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruteurRepository extends JpaRepository<JpaRecruteur, Integer> {
    Optional<JpaRecruteur> findById(int id);
}
